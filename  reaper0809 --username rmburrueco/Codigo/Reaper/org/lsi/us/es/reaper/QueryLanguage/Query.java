/* Authors:
 *  
 * Pablo Iñigo Blasco
 * Rosa María Burrueco
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

	public void executeQuery(Form form, IFormFiller formFiller)
			throws JavaScriptException {
		List<Simple> partialSimpleAssignments = new ArrayList<Simple>();
		for (Assignment assignment : this.getAssignments())
			if (assignment.getClass() == Simple.class)
				partialSimpleAssignments.add((Simple) assignment);

		List<List<Group>> groupSets = generateAllGroupSetsCombination();
		List<Simple> fullUndependantSimpleAssignmentSet = new ArrayList<Simple>();

		Integer countRequests = 0;

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
	}

	private void processFullAssignmentSet(List<Simple> simpleAssignments,
			Form form, IFormFiller formFiller, Integer countRequests)
			throws JavaScriptException {
		int[] currentValue = new int[simpleAssignments.size()];
		boolean end = false;

		int simpleAssignmentIndex = 0;
		SortedMap<Simple, Value> assignmentsMap = new TreeMap<Simple, Value>(
				new simpleAssignmentOrderComparator());

		while (!end) {

			Simple currentSimpleAssignment = simpleAssignments
					.get(simpleAssignmentIndex);
			int valueIndex = currentValue[simpleAssignmentIndex];

			assignmentsMap.put(currentSimpleAssignment, currentSimpleAssignment
					.getValues().get(valueIndex));

			if (simpleAssignmentIndex == simpleAssignments.size() - 1) {

				submitActivity(assignmentsMap, form, formFiller);

				countRequests++;

				end = true;
				for (int j = 0; j < currentValue.length && end; j++)
					if (currentValue[j] != simpleAssignments.get(j).getValues()
							.size() - 1)
						end = false;
			}

			currentValue[simpleAssignmentIndex] = (valueIndex + 1)
					% currentSimpleAssignment.getValues().size();
			simpleAssignmentIndex = (simpleAssignmentIndex + 1)
					% simpleAssignments.size();

		}
	}

	private void submitActivity(Map<Simple, Value> assignmentsMap, Form form,
			IFormFiller formFiller) {
		ReapingProcess.nextAttempt();
		form.getReachFormMethod().navigateToForm();
		String calculatedValue = null;
		this.launcEvent(EventEnumeration.fieldAssignmentsSetBegin);
		try {
			for (Entry<Simple, Value> e : assignmentsMap.entrySet()) {
				Field f = e.getKey().getField();

				ReapingProcess.getFormFiller().registerVariable(
						ScriptVariable.currentFieldLocator,
						f.getLocator().getExpression().replace("\"", "\\\"")
								.replace("'", "\\'"));
				ReapingProcess.getFormFiller().registerVariable(
						ScriptVariable.currentField, f.getFieldId());

				this.launcEvent(EventEnumeration.fieldAssignmentBegin);
				calculatedValue = e.getValue().evaluate();
				f.setValue(e.getValue(), calculatedValue, formFiller);
				ReapingProcess.getFormFiller().registerVariable(
						ScriptVariable.currentValue, calculatedValue);

				formFiller.registerVariable(ScriptVariable.currentValue,
						calculatedValue);
				this.launcEvent(EventEnumeration.fieldAssignmentEnd);
				ReapingProcess.getFormFiller().registerVariable(
						ScriptVariable.currentFieldLocator, null);
				ReapingProcess.getFormFiller().registerVariable(
						ScriptVariable.currentField, null);
				ReapingProcess.getFormFiller().registerVariable(
						ScriptVariable.currentValue, null);
			}
		} catch (JavaScriptException e) {
			LogSystem.settingValueFail(e);
			LogSystem.scriptEventFailed(e, this);
			return;
		} catch (ReapingProccessException e) {
			LogSystem.settingValueFail(e);
			return;
			
		}
		catch (Exception ex) //tipicamente una timeoutException
		{
			LogSystem.settingValueFail(new ReapingProccessException(ex));
			return;
			
		}
		finally {
			ReapingProcess.getFormFiller().registerVariable(
					ScriptVariable.currentFieldLocator, null);
			ReapingProcess.getFormFiller().registerVariable(
					ScriptVariable.currentField, null);
			ReapingProcess.getFormFiller().registerVariable(
					ScriptVariable.currentValue, null);
		}
		try
		{
			this.launcEvent(EventEnumeration.fieldAssignmentsSetBegin);
			formFiller.submit(form.getSubmit());
		}
		catch(Exception ex)//tipicamente una timeoutException
		{
			LogSystem.submitFailed(new ReapingProccessException(ex));
		}

		boolean nextActionOrResult = true;
		for (Result r : form.getResults()) {
			if (r.applicable(formFiller.getCurrentHtmlContent(), formFiller.getCurrentUrl())) {
				try {
					for (Action a : r.getActions()) {

						formFiller.registerVariable(
								ScriptVariable.currentAction, a.getClass()
										.getSimpleName());
						nextActionOrResult = a.apply(formFiller, this);
						if (!nextActionOrResult)
							break;
						formFiller.registerVariable(
								ScriptVariable.currentAction, null);
					}
					if (!nextActionOrResult)
						break;
				} catch (ReapingProccessException e) {
					LogSystem.applingResultFail(e);
				} finally {
					formFiller.registerVariable(ScriptVariable.currentAction,
							null);
				}
			}

		}
	}

	public void launcEvent(EventEnumeration eventName) {
		if (events != null) {
			for (Event e : events) {
				if (e.getName() == eventName) {
					try {
						IFormFiller filler = ReapingProcess.getFormFiller();
						filler.registerVariable(ScriptVariable.currentEvent,
								eventName.toString());
						filler.evalScript(e.getScriptExpression());
						filler.registerVariable(ScriptVariable.currentEvent,
								null);
						filler.waitForPageToLoad(Configurations.submitWaitMilliseconds);
					} catch (Exception ex) {
						LogSystem.scriptEventFailed(ex, this);
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

	public boolean validate(List<String> errors, Form f) {
		// Comprobar que todos los campos del lenguaje de queries
		// existen en el lenguaje de forms.
		boolean error = false;

		for (Assignment a : getAssignments())
			error |= a.validate(errors, f);

		try {
			ReapingProcess.getFormFiller().importScripts(getJavaScriptImports());
		} catch (LoadingModelException ex) {
			error = true;
			errors.add(ex.toString());
		}

		return error;
	}

}
