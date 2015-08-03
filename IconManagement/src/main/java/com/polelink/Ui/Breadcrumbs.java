package com.polelink.Ui;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import com.vaadin.navigator.Navigator.UriFragmentManager;
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;


/**
 * A {@link Component} that autonomously handles URI Fragment changes, their
 * repercussions in the application. Allows also other objects to control the
 * flow of the Application.
 * 
 * @author Henrik Paul / IT Mill Ltd
 */
public class Breadcrumbs extends UriFragmentManager implements UriFragmentChangedListener
         {

    public Breadcrumbs(Page page) {
		super(page);
		// TODO Auto-generated constructor stub
		page.addUriFragmentChangedListener(this);
	}

	/**
     * The interface that enables {@link Breadcrumbs} to step through the
     * application's visual structure.
     * 
     * @author Henrik Paul / IT Mill Ltd
     */
    public interface CrumbTrail {
        /**
         * Make visual changes to the application, and advance the application's
         * state forwards.
         * 
         * @param path
         *            A string representing a desired path to be taken by the
         *            view. A part of the URI fragment.
         * @return The object that handles the view changes from this point on.
         *         <code>null</code> if <code>path</code> leads to a dead-end.
         */
        public CrumbTrail walkTo(String path);
    }

    private static final long serialVersionUID = -406158772559095506L;

    /**
     * The character used to separate parts from the path. Doesn't need to be /.
     */
    private static final String FRAGMENT_SEPARATOR = "/";

//    public Breadcrumbs() {
//        addListener(this);
//    }
//    public Breadcrumbs(){
//    	
//    }

    // Implementing FragmentChangedListener
    public void uriFragmentChanged(UriFragmentChangedEvent source) {
        CrumbTrail trail = null;
System.out.println("here");
        try {
            // Get the root of the trail - the application itself.
            trail = (CrumbTrail) UI.getCurrent();

        } catch (ClassCastException e) {
            // An assertion would probably be more suitable in a production
            // environment.
            System.err.println(UI.getCurrent().getClass().getName()
                    + " must implement " + CrumbTrail.class.getName());
            return;
        }

        if (trail != null) {
            final String[] paths = splitFragmentIntoPaths();

            // Give each portion of the fragment in turn to each component
            for (String path : paths) {
                trail = trail.walkTo(path);

                if (trail == null) {
                    break;
                }
            }
        } else {
            // This would indicate sever problems with Vaadin.
            System.err.println("getApplication() returned null");
        }
    }

    /**
     * Splits the URI Fragment into its composite parts, using
     * {@link #FRAGMENT_SEPARATOR} as its delimiter.
     * 
     * @return
     */
    private String[] splitFragmentIntoPaths() {
        return getCleanFragment().split(FRAGMENT_SEPARATOR);
    }

    /**
     * Walk the application forwards.
     * 
     * @param path
     *            A piece of the URI fragment to be added.
     */
    public void walkTo(String path) {
    	System.out.println("bred walkTo");
        String fragment = getCleanFragment();
        System.out.println(fragment+"fragment");
        if (fragment.length() > 0) {
            setFragment(fragment.concat(FRAGMENT_SEPARATOR + path));
        } else {
            setFragment(path);
        }
    }

    /**
     * Take one step back in the crumb trail.
     */
    public void walkBack() {
        walkBack(1);
    }

    /**
     * Take a certain amount of steps back in the trail.
     * 
     * @param steps
     *            The amount of steps to be taken. If &lt; 1, this method does
     *            nothing. If <code>steps</code> exceeds the amount of paths on
     *            the breadcrumbs, the uri fragment will be emptied.
     */
    public void walkBack(int steps) {
        if (steps > 0) {
            String fragment = getCleanFragment();
            if (fragment.length() > 0) {
                LinkedList<String> paths = new LinkedList<String>(Arrays
                        .asList(splitFragmentIntoPaths()));

                String newFragment = "";
                System.out.println(paths+"paths");
                try {
                    for (int i = 0; i < steps; i++) {
                        paths.removeLast();
                    }

                    newFragment = paths.removeFirst();
                    System.out.println(newFragment+"newFragment");
                    for (String path : paths) {
                        newFragment += FRAGMENT_SEPARATOR + path;
                    }
                } catch (NoSuchElementException e) {
                    // If paths is empty, it's ok to leave the new fragment
                    // empty
                }

                setFragment(newFragment);
            }
        }
    }

    /**
     * Get the current fragment without superfluous {@link #FRAGMENT_SEPARATOR}
     * s.
     * 
     * @return A cleaned URI fragment.
     */
    private String getCleanFragment() {
        String fragment = getFragment();
        if (fragment != null && fragment.length() > 0) {
            fragment = fragment
                    .replaceFirst("^" + FRAGMENT_SEPARATOR + "+", "");
            fragment = fragment.replaceFirst(FRAGMENT_SEPARATOR + "+$", "");

            return fragment;
        } else {
            return "";
        }
    }
}
