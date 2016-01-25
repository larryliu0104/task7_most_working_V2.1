package controller.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.Log;
import util.Util;

public abstract class Action {
	private static final String TAG = "ACTION";

	public abstract String getName();

	// Returns the name of the jsp used to render the output.
	public abstract String perform(HttpServletRequest request);

	// Class methods to manage dispatching to Actions
	private static Map<String, Action> hash = new HashMap<String, Action>();

	public static void add(Action a) {
		synchronized (hash) {
			if (hash.get(a.getName()) != null) {
				Log.e(TAG, "Action " + a.getName() + " exists");
			}
			hash.put(a.getName(), a);
			Log.i("Action", "add action " + a.getName());
		}
	}

	public static String perform(String name, HttpServletRequest request) {
		Action a;
		synchronized (hash) {
			a = hash.get(name);
		}
		if (a == null) {
			return null;
		}
		Log.i(TAG,
		    "Perform action " + a.getName() + " at time " + Util.getCurrentTime());
		return a.perform(request);
	}
}
