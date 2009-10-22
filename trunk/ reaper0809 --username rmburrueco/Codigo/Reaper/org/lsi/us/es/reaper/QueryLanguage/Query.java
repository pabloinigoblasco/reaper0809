/* 
 * Authors:  
 * 	Pablo Iñigo Blasco
 * 	Rosa María Burrueco
 *  
 * Directed by:
 *  	Rafael Corchuelo Gil
 *  	Inmaculada Hernández Salmerón
 *  
 * Universidad de Sevilla 2009
 *  
 * */

package org.lsi.us.es.reaper.QueryLanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.lsi.us.es.reaper.Core.Configurations;
import org.lsi.us.es.reaper.Core.IFormFiller;
import org.lsi.us.es.reaper.Core.LogSystem;
import org.lsi.us.es.reaper.Core.ReapingProcess;
import org.lsi.us.es.reaper.Core.simpleAssignmentOrderComparator;
import org.lsi.us.es.reaper.Core.Exceptions.JavaScriptException;
import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;
import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;
import org.lsi.us.es.reaper.FormLanguage.Field;
import org.lsi.us.es.reaper.FormLanguage.Form;
import org.lsi.us.es.reaper.FormLanguage.Action;
import org.lsi.us.es.reaper.FormLanguage.Result;

import sun.util.logging.resources.logging;

public class Query {
	String formURL;
	List<Assignment> assignments;
	List<JavaScript> javaScriptImports;
	List<Event> events;

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<JavaScript> getJavaScriptImports() {
		return javaScriptImports;
	}

	public void setJavaScriptImports(List<JavaScript> javaScriptImports) {
		this.javaScriptImports = javaScriptImports;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	public String getFormURL() {
		return formURL;
	}

	public void setFormURL(String formURL) {
		this.formURL = formURL;
	}

	public boolean validate(List<String> errors, Form f) {
		// Comprobar que todos los campos del lenguaje de queries
		// existen en el lenguaje de forms.
		boolean error = false;

		if (!f.getIdentificationUrl().equals(this.getFormURL())) {
			error = true;
			errors
					.add("Query formUrl attribute must be the same than Form identificationUrl");
		}

		for (Assignment a : getAssignments())
			error |= a.validate(errors, f);

		try {
			ReapingProcess.getFormFiller()
					.importScripts(getJavaScriptImports());
		} catch (LoadingModelException ex) {
			error = true;
			errors.add("Error cargando javaScripts especificados:\n"
					+ ex.toString());
		}

		return error;
	}

	private void processFullAssignmentSet(List<Simple> simpleAssignments,
			Form form, IFormFiller formFiller, Integer countRequests)
			throws JavaScriptException {
		int[] currentValue = new int[simpleAssignments.size()];
		boolean end = false;
		boolean lastTime=false;

		int simpleAssignmentIndex = 0;
		SortedMap<Simple, Value> assignmentsMap = new TreeMap<Simple, Value>(
				new simpleAssignmentOrderComparator());

		while (!end) {

			//se escoje un campo
			Simple currentSimpleAssignment = simpleAssignments.get(simpleAssignmentIndex);
			
			//se escoje el valor a asignar
			int valueIndex = currentValue[simpleAssignmentIndex];

			//se anota el par campo, valor
			assignmentsMap.put(currentSimpleAssignment, currentSimpleAssignment
					.getValues().get(valueIndex));

			//en el caso de haber completado el mapa de asignaciones
			if (simpleAssignmentIndex == simpleAssignments.size() - 1) 
			{
				ProcessQueryActivity(assignmentsMap, form, formFiller);

				countRequests++;
				end = true;
				//en el caso que todos los campos tengan asignado su último valor, se ha tratado de la ultima vez
				for (int j = 0; j < currentValue.length && end; j++)
					if (currentValue[j] != simpleAssignments.get(j).getValues().size()-1)
						end = false;
				
				for(int j=0;j<currentValue.length;j++)
					currentValue[j] = (currentValue[j]+1) % simpleAssignments.get(j).getValues().size();
			}

			simpleAssignmentIndex = (simpleAssignmentIndex + 1)
					% simpleAssignments.size();

		}
	}

	private void ProcessQueryActivity(Map<Simple, Value> assignmentsMap,
			Form form, IFormFiller formFiller) {
		ReapingProcess.nextAttempt();
		form.getReachFormMethod().navigateToForm();

		LogSystem.NotifyStartingSubmitActivity(assignmentsMap);

		// variables para ser utilizadas en caso de excepción
		Field f = null;
		String calculatedValue = null;

		this.launchEvent(EventEnumeration.queryBegin);

		for (Entry<Simple, Value> e : assignmentsMap.entrySet()) {
			try {
				f = e.getKey().getField();

			
				ReapingProcess.getFormFiller().setVariableValue(
						ScriptVariable.currentFieldLocator,
						f.getLocator().getExpression());

				ReapingProcess.getFormFiller().setVariableValue(
						ScriptVariable.currentField, f.getFieldId());

				this.launchEvent(EventEnumeration.fieldAssignmentBegin);
				calculatedValue = e.getValue().evaluate();
				if (e.getValue().isJavascriptExpression())
					LogSystem.notifyAssignmentEvaluated(e.getValue().getVal(),
							calculatedValue, f);

				f.setValue(e.getValue(), calculatedValue, formFiller);
				ReapingProcess.getFormFiller().setVariableValue(
						ScriptVariable.currentValue, calculatedValue);

				formFiller.setVariableValue(ScriptVariable.currentValue,
						calculatedValue);
				this.launchEvent(EventEnumeration.fieldAssignmentFinished);

			} catch (JavaScriptException ex) {
				LogSystem.settingValueFail(ex, f, calculatedValue);
				LogSystem.scriptEventFailed(ex, this);
				return;
			} catch (ReapingProccessException ex) {
				LogSystem.settingValueFail(ex, f, calculatedValue);
				return;

			} catch (Exception ex) // tipicamente una timeoutException
			{
				LogSystem.settingValueFail(new ReapingProccessException(ex), f,
						calculatedValue);
				return;

			} finally {
				ReapingProcess.getFormFiller().setVariableValue(
						ScriptVariable.currentFieldLocator, null);
				ReapingProcess.getFormFiller().setVariableValue(
						ScriptVariable.currentField, null);
				ReapingProcess.getFormFiller().setVariableValue(
						ScriptVariable.currentValue, null);
			}
		}

		try {
			this.launchEvent(EventEnumeration.submitBegin);
			formFiller.submit(form.getSubmit());
			this.launchEvent(EventEnumeration.submitFinished);
		} catch (Exception ex)// tipicamente una timeoutException
		{
			LogSystem.submitFailed(new ReapingProccessException(ex));
		}

		this.launchEvent(EventEnumeration.queryFinished);

		boolean nextActionOrResult = true;
		for (Result r : form.getResults()) {
			if (r.applicable(formFiller.getCurrentHtmlContent(), formFiller
					.getCurrentUrl())) {
				try {
					for (Action a : r.getActions()) {

						formFiller.setVariableValue(
								ScriptVariable.currentAction, a.getClass()
										.getSimpleName());
						nextActionOrResult = a.apply(formFiller, this);
						if (!nextActionOrResult)
							break;
						formFiller.setVariableValue(
								ScriptVariable.currentAction, null);
					}
					if (!nextActionOrResult)
						break;
				} catch (ReapingProccessException e) {
					LogSystem.applingResultFail(e);
				} finally {
					formFiller.setVariableValue(ScriptVariable.currentAction,
							null);
				}
			}

		}
	}

	public void executeQuery(Form form, IFormFiller formFiller)
			throws JavaScriptException {
		List<Simple> partialSimpleAssignments = new ArrayList<Simple>();
		for (Assignment assignment : this.getAssignments())
			if (assignment.getClass() == Simple.class)
				partialSimpleAssignments.add((Simple) assignment);

		List<List<Group>> groupSets = generateAllGroupSetsCombination();
		List<Simple> fullUndependantSimpleAssignmentSet = new ArrayList<Simple>();

		Integer countRequests = 0;
		launchEvent(EventEnumeration.reapingProcessBegin);
		if (groupSets.size() > 0) {
			for (List<Group> gs : groupSets) {
				fullUndependantSimpleAssignmentSet.clear();
				fullUndependantSimpleAssignmentSet
						.addAll(partialSimpleAssignments);
				for (Group g : gs)
					fullUndependantSimpleAssignmentSet.addAll(g
							.getSimpleAssigments());

				processFullAssignmentSet(fullUndependantSimpleAssignmentSet,
						form, formFiller, countRequests);
			}
		} else {
			fullUndependantSimpleAssignmentSet.addAll(partialSimpleAssignments);
			processFullAssignmentSet(fullUndependantSimpleAssignmentSet, form,
					formFiller, countRequests);
		}
		launchEvent(EventEnumeration.reapingProcessFinished);
	}

	public void launchEvent(EventEnumeration eventName) {

		if (events != null) {
			for (Event e : events) {
				if (e.getName().equals(eventName)) 
				{
					IFormFiller filler = ReapingProcess.getFormFiller();
					try {
						if(e.getScriptExpression()!=null)
						{
							
							String res;
							do{
								filler.setVariableValue(ScriptVariable.currentEvent,
										eventName.toString());
								 res= filler.evalScript(e.getScriptExpression());
								 
								 if(!res.equals("null") && !res.equals("false"))
								 	Thread.sleep(1000);
								 else
									 break;
								 
							}while(true);
							
							try {
								filler.waitForPageToLoad(Configurations.afterEventsCodeWaitMilliseconds);
							} catch (Exception ex) {
							}
						}
					} catch (Exception ex) {
						if (!eventName.equals(EventEnumeration.scriptException
								.name()))
							LogSystem.scriptEventFailed(ex, this);
					}
					finally
					{
						filler.setVariableValue(ScriptVariable.currentEvent,null);
					}

				}

			}
		}

	}

	// ----------------- auxiliar methods-----------------------

	private List<List<Group>> generateAllGroupSetsCombination() {

		List<Dependant> dependantAssignments = new ArrayList<Dependant>();
		List<List<Group>> tupleSet = new ArrayList<List<Group>>();
		for (Assignment assignment : this.getAssignments())
			if (assignment.getClass() == Dependant.class
					&& ((Dependant) assignment).getGroups().size() > 0)
				dependantAssignments.add((Dependant) assignment);

		if (dependantAssignments.size() > 0) {
			for (Group group : dependantAssignments.get(0).getGroups()) {
				List<Group> tuple = new ArrayList<Group>();
				tuple.add(group);
				tupleSet.add(tuple);
			}
			auxGenerateAllGroupSet(dependantAssignments, 1, tupleSet);
		}
		return tupleSet;
	}

	private void auxGenerateAllGroupSet(List<Dependant> dependantList,
			int currentDepedency, List<List<Group>> tuplesList) {
		if (currentDepedency < dependantList.size()) {
			List<Group> groups = dependantList.get(currentDepedency)
					.getGroups();
			List<List<Group>> newElements = new ArrayList<List<Group>>();
			for (List<Group> currentTuple : tuplesList) {
				for (int i = 1; i < groups.size(); i++) {
					// duplicamos la tupla seleccionada
					List<Group> newList = new ArrayList<Group>(currentTuple);
					// y agregamos el grupo actual
					newList.add(groups.get(i));
					// añadimos la nueva tupla creada
					newElements.add(newList);
				}
				// para la tupla que ha servido de prototipo no la duplicamos
				// sino que directamente se añadie el primer grupo de este
				// conjunto dependiente
				currentTuple.add(groups.get(0));
			}
			tuplesList.addAll(newElements);
			auxGenerateAllGroupSet(dependantList, ++currentDepedency,
					tuplesList);
		}
	}

}
