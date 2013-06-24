/*
 * Copyright (c) 2005-2013 Clark & Parsia, LLC. <http://www.clarkparsia.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.complexible.common.io;

import java.io.DataInput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import com.google.common.base.Charsets;

/**
 * <p>Utility class which contains readers for the standard primitives & serializable types.</p>
 *
 * @author  Michael Grove
 * @since   1.0
 * @version 1.0
 */
public final class DataInputProcessors {

    /**
     * No instances
     */
    private DataInputProcessors() {
        throw new AssertionError();
    }

    public static <T extends Serializable> DataInputProcessor<T, DataInput> serializable() {
        return new SerializableDataInputProcessor<T>();
    }

    public static enum Primitives implements DataInputProcessor {
        Int(new DataInputProcessor<Integer, DataInput>() {
            @Override
            public Integer processInput(final DataInput theInput) throws IOException {
                return theInput.readInt();
            }
        }),
        Long(new DataInputProcessor<Long, DataInput>() {
            @Override
            public Long processInput(final DataInput theInput) throws IOException {
                return theInput.readLong();
            }
        }),
        Float(new DataInputProcessor<Float, DataInput>() {
            @Override
            public Float processInput(final DataInput theInput) throws IOException {
                return theInput.readFloat();
            }
        }),
        String(new DataInputProcessor<String, DataInput>() {
            @Override
            public String processInput(final DataInput theInput) throws IOException {
                byte[] aBytes = new byte[theInput.readInt()];

                theInput.readFully(aBytes);

                return new java.lang.String(aBytes, Charsets.UTF_8);
            }
        });

        private final DataInputProcessor mInputProcessor;

        private Primitives(final DataInputProcessor theInputProcessor) {
            mInputProcessor = theInputProcessor;
        }

        @Override
        public Object processInput(final DataInput theInput) throws IOException {
            return mInputProcessor.processInput(theInput);
        }
    }

    private static class SerializableDataInputProcessor<T extends Serializable> implements DataInputProcessor<T, DataInput> {

        /**
         * @inheritDoc
         */
        @Override
        @SuppressWarnings("unchecked")
        public T processInput(final DataInput theInput) throws IOException {
            try {
                return (T) new ObjectInputStream(DataInputToInputStreamAdapter.create(theInput)).readObject();
            }
            catch (ClassNotFoundException e) {
                throw new IOException("Malformed object", e);
            }
        }
    }
}
