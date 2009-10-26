// Copyright (c) 2005 - 2009, Clark & Parsia, LLC. <http://www.clarkparsia.com>

package com.clarkparsia.utils.collections;

import com.clarkparsia.utils.Predicate;
import com.clarkparsia.utils.Function;

import java.util.Set;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collection;
import java.util.Collections;

/**
 * Title: CollectionUtil<br/>
 * Description: Utility methods for using and working with collections.<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: Apr 21, 2009 10:15:38 AM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class CollectionUtil {

	public static <T> Set<T> set(Iterable<T> theIter) {
		return set(theIter.iterator());
	}

    public static <T> Set<T> set(Iterator<T> theIter) {
        Set<T> aSet = new LinkedHashSet<T>();

        while (theIter.hasNext())
            aSet.add(theIter.next());

        return aSet;
    }

	public static <T> List<T> list(Iterable<T> theIter) {
		return list(theIter.iterator());
	}

    public static <T> List<T> list(Iterator<T> theIter) {
        List<T> aList = new ArrayList<T>();

        while (theIter.hasNext()) {
            aList.add(theIter.next());
		}

        return aList;
    }

	public static <T> Iterable<T> iterable(final Iterator<T> theIter) {
		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return theIter;
			}
		};
	}

    /**
     * Checks to see if any elements of one set are present in another.
     * First parameter is the list of elements to search for, the second
     * parameter is the list to search in.  Basically tests to see if the two sets intersect
     * @param theList Set the elements to look for
     * @param toSearch Set the search set
     * @return boolean true if any element in the first set is present in the second
     */
    public static <T> boolean containsAny(Collection<T> theList, Collection<T> toSearch) {

        if (toSearch.isEmpty()) {
            return false;
        }
        else
        {
            for (T aObj : theList) {
                if (toSearch.contains(aObj)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static <T> boolean containsAll(Collection<T> theList, Collection<T> toSearch) {

        if (toSearch.isEmpty()) {
            return true;
        }
        else
        {
            for (T aObj : toSearch) {
                if (!theList.contains(aObj)) {
                    return false;
                }
            }
            return true;
        }
    }

	    /**
     * Convienence method for creating a set that is the union of N other sets
     * @param theSets the sets to create a union of
     * @param <T> the type of the sets
     * @return a set which is the union of all the passed in sets
     */
    public static <T> Set<T> union(Set<T>... theSets) {
        Set<T> aUnion = new HashSet<T>();

        for (Set<T> aSet : theSets) {
            aUnion.addAll(aSet);
        }

        return aUnion;
    }

    /**
     * Convienence method for creating a set that is the intersection of all N sets
     * @param theSets the sets to create the intersection from
     * @param <T> the type of the sets
     * @return a new set which is the intersection of the provided sets
     */
    public static <T> Set<T> intersection(Set<T>... theSets) {
        Set<T> aIntersection = new HashSet<T>();

        boolean aFirst = true;
        for (Set<T> aSet : theSets) {
            if (aFirst) {
                aFirst = false;
                aIntersection.addAll(aSet);
            }
            else {
                aIntersection.retainAll(aSet);
            }
        }

        return aIntersection;
    }

    /**
     * Returns true if two collections have the same contents.  This is used to compare two collections which may or may
     * not be the same type, such as comparing a list and a set, which normally will not be equals even if they
     * contain the same set of values.  This does an equals comparision on the contents of the collections.
     * @param theList the first collection to compare
     * @param theCompare the second collection to compare
     * @param <T> the object type in the collections
     * @return true if they have the same contents, false otherwise
     */
    public static <T> boolean contentsEqual(Collection<T> theList, Collection<T> theCompare) {
        if (theList.size() != theCompare.size()) {
            return false;
        }

        for (T aListElem : theList) {
            Iterator aCompareIter = theCompare.iterator();

            boolean found = false;
            while (aCompareIter.hasNext()) {
                Object aCompareItem = aCompareIter.next();

                if (aListElem.equals(aCompareItem)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                return false;
            }
        }

        return true;
    }

	/**
	 * Filter the contents of a collection based on the collection filter provided
	 * @param theCollection the collection to filter
	 * @param theFilter the filter to use
	 * @param <T> the type of objects in the collection
	 * @return a copy of the collection, but filtered to only include things that passed the filter.
	 */
	public static <T> Collection<T> filter(Collection<T> theCollection, Predicate<T> theFilter) {
		try {
			Collection<T> aNewCollection = (Collection<T>) theCollection.getClass().newInstance();

			for (T aObj : theCollection) {
				if (theFilter.accept(aObj)) {
					aNewCollection.add(aObj);
				}
			}

			return aNewCollection;
		}
		catch (InstantiationException e) {
			// i dont think this can happen, but hey, we'll track it!
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static <I, O> Collection<O> transform(Collection<I> theCollection, Function<I,O> theTransformer) {
		List<O> aNewCollection = new ArrayList<O>();

		for (I aObj : theCollection) {
			aNewCollection.add(theTransformer.apply(aObj));
		}

		return aNewCollection;
	}
}
