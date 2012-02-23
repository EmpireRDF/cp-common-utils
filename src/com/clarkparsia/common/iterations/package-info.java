/**
 * Contains classes for defining and using {@link Iteration Iterations} which are analogous to
 * standard Java {@link Iterator Iterators} with the exception that they are {@link Iteration#close closeable}
 * and can throw an exception when calling {@link Iteration#hasNext} or {@link Iteration#next}.
 */
package com.clarkparsia.common.iterations;