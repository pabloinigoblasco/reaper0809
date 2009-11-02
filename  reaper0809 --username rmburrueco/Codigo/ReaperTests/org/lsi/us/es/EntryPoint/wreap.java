package org.lsi.us.es.EntryPoint;

import org.lsi.us.es.reaper.Core.Configurations;
import org.lsi.us.es.reaper.Core.LogSystem;
import org.lsi.us.es.reaper.Core.ReapingProcess;



public class wreap {

	public static void main(String[] args) {

		if (findOption("--browser", args)) {
			Configurations.SeleniumNavigator = option;
			LogSystem.logConsole("browser: " + option);
		}
		if (findOption("--out", args)) {
			Configurations.OutputDirectory = option;
			LogSystem.logConsole("out: " + option);
		}
		if (findOption("--speed", args)) {
			Configurations.BrowserSpeed = option;
			LogSystem.logConsole("speed: " + option);
		}
		if (findOption("--submitMaxWait", args)) {
			Configurations.submitWaitMilliseconds = option;
			LogSystem.logConsole("submitMaxWait: " + option);
		}
		if (findOption("--eventMaxWait", args)) {
			Configurations.afterEventsCodeWaitMilliseconds = option;
			LogSystem.logConsole("eventMaxWait: " + option);
		}
		if (findOption("--fm", args)) {
			Configurations.FormModelMappingFile = option;
			LogSystem.logConsole("form mapping: " + option);
		}
		if (findOption("--qm", args)) {
			Configurations.QueryModelMappingFile = option;
			LogSystem.logConsole("query mapping: " + option);
		}

		if (args.length >= 2 && !args[0].contains("--")
				&& !args[1].contains("--")) {
			ReapingProcess p = new ReapingProcess();
			p.start(args[0], args[1]);

		} else if (args.length == 1 && args[0].equals("--help")) {
			helpMenu();
		}

		else if (findOption("--version", args)) {
			System.out.println("wreap v0.9.10. University of Seville. 2009.");
			System.out.println("Authors:");
			System.out.println("\tPablo Iñigo Blasco");
			System.out.println("\tRosa María Burrueco");
			System.out.println("Advisors:");
			System.out.println("\tRafael Corchuelo Gil");
			System.out.println("\tInmaculada Hernandez Salmerón");

		}

		else {
			System.out.println("Incorrect usage.");
			System.out.println("wreap --help for usage overview");
			System.out.println("wreap v0.9.10. University of Seville. 2009.");
		}
	}

	private static String option = null;

	private static boolean findOption(String optionName, String[] args) {
		int i = 0;
		boolean found = false;
		option = null;

		for (; i < args.length && !found; i++) {
			if (args[i].equals(optionName)) {
				if (i + 1 >= args.length)
					option = "";
				else
					option = args[i + 1];
				found = true;
			}
		}
		return found;
	}

	private static void helpMenu() {

		System.out.println("USAGE:");
		System.out
				.println("wreap [formModel] [queryModel] [OPTIONAL PARAMETERS]");
		System.out.println();
		System.out.println("OPTIONAL PARAMETERS:");
		System.out.println();
		System.out
				.println("--browser\tSeleniumNavigator, selenium browser type, default value='*firefox'");
		System.out
				.println("\t\texample: wreap --browser *opera [more parameters]");
		System.out.println();
		System.out
				.println("--out\t\tOutputDirectory, default value= './Results/'");
		System.out
				.println("\t\texample wreap --out' ./resultOutput [more parameters]");
		System.out.println();
		System.out
				.println("--speed\t\tbrowser speed between actions in milliseconds, default value='10'");
		System.out.println("\t\texample wreap --speed 20");
		System.out.println();
		System.out
				.println("--submitMaxWait\tmaximum waiting time in millisecs after the main submit or prodict detail navigation in IterateHub actions");
		System.out
				.println("\t\texample: wreap --submitMaxWait 20000 [more parameters]");
		System.out.println();
		System.out
				.println("--eventMaxWait\tmaximum waiting time in millisecs after events");
		System.out
				.println("\t\texample: wreap --eventMaxWait 20000 [more parameters]");
		System.out.println();
		System.out
				.println("--fm\t\tFormModelMappingFile, castor xml mapping format for the form model");
		System.out
				.println("\t\texample: wreap --fm myfformat.xml [more parameters]");
		System.out.println();
		System.out
				.println("--qm\t\tQueryModelMappingFile, castor xml mapping format for the query model");
		System.out
				.println("\t\texample: wreap --qm myqformat.xml [more parameters]");
		System.out.println();
		System.out.println("ABOUT:");
		System.out.println();
		System.out.println("--version\tversion and authors);");
		System.out.println("\t\texample wreap --version");
	}
}
