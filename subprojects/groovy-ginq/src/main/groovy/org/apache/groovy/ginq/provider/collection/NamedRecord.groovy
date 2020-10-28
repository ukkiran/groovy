/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.groovy.ginq.provider.collection


import groovy.transform.CompileStatic
import groovy.transform.stc.POJO

/**
 * Represents named record
 *
 * @since 4.0.0
 */
@POJO
@CompileStatic
class NamedRecord<E, T> extends NamedTuple<E> {
    private final Map<String, Object> sourceRecordCache = new HashMap<>(4)
    private T sourceRecord
    private List<String> aliasList

    NamedRecord(List<E> elementList, List<String> nameList, List<String> aliasList = Collections.emptyList()) {
        super(elementList, nameList)
        this.aliasList = aliasList
    }

    @Override
    def getAt(String name) {
        if (exists(name)) {
            def value = super.getAt(name)
            if (null != value) return value
        }

        return sourceRecordCache.computeIfAbsent(name, n -> findSourceRecordByName(n))
    }

    private findSourceRecordByName(String name) {
        if (!aliasList.contains(name)) {
            throw new GroovyRuntimeException("Failed to find: $name")
        }

        def accessPath = sourceRecord

        if (accessPath !instanceof Tuple2) return accessPath

        for (int i = aliasList.size() - 1; i >= 0; i--) {
            String alias = aliasList.get(i)
            if (name == alias) {
                if (accessPath instanceof Tuple2) {
                    return accessPath.get(1)
                } else {
                    return accessPath
                }
            } else if (accessPath instanceof Tuple2) {
                accessPath = accessPath.get(0)
            }
        }

        return accessPath
    }

    T sourceRecord() {
        return sourceRecord
    }

    NamedRecord<E, T> sourceRecord(T sourceRecord) {
        this.sourceRecord = sourceRecord
        return this
    }
}
