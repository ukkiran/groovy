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


import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.junit.Test

import java.util.stream.Collectors
import java.util.stream.Stream

import static org.apache.groovy.ginq.provider.collection.Queryable.from

@CompileStatic
class QueryableCollectionTest {
    @Test
    void testFrom() {
        assert [1, 2, 3] == from(Stream.of(1, 2, 3)).toList()
        assert [1, 2, 3] == from(Arrays.asList(1, 2, 3)).toList()
        assert [1, 2, 3] == from(from(Arrays.asList(1, 2, 3))).toList()
    }

    @Test
    void testInnerJoin0() {
        def nums1 = [1, 2, 3]
        def nums2 = [1, 2, 3]
        def result = from(nums1).innerJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[1, 1], [2, 2], [3, 3]] == result
    }

    @Test
    void testInnerJoin1() {
        def nums1 = [1, 2, 3]
        def nums2 = [2, 3, 4]
        def result = from(nums1).innerJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[2, 2], [3, 3]] == result
    }

    @Test
    void testLeftJoin0() {
        def nums1 = [1, 2, 3]
        def nums2 = [1, 2, 3]
        def result = from(nums1).leftJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[1, 1], [2, 2], [3, 3]] == result
    }

    @Test
    void testRightJoin0() {
        def nums2 = [1, 2, 3]
        def nums1 = [1, 2, 3]
        def result = from(nums1).rightJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[1, 1], [2, 2], [3, 3]] == result
    }

    @Test
    void testLeftJoin1() {
        def nums1 = [1, 2, 3]
        def nums2 = [2, 3, 4]
        def result = from(nums1).leftJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[1, null], [2, 2], [3, 3]] == result
    }

    @Test
    void testRightJoin1() {
        def nums2 = [1, 2, 3]
        def nums1 = [2, 3, 4]
        def result = from(nums1).rightJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[null, 1], [2, 2], [3, 3]] == result
    }

    @Test
    void testLeftJoin2() {
        def nums1 = [1, 2, 3, null]
        def nums2 = [2, 3, 4]
        def result = from(nums1).leftJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[1, null], [2, 2], [3, 3], [null, null]] == result
    }

    @Test
    void testRightJoin2() {
        def nums2 = [1, 2, 3, null]
        def nums1 = [2, 3, 4]
        def result = from(nums1).rightJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[null, 1], [2, 2], [3, 3], [null, null]] == result
    }

    @Test
    void testLeftJoin3() {
        def nums1 = [1, 2, 3, null]
        def nums2 = [2, 3, 4, null]
        def result = from(nums1).leftJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[1, null], [2, 2], [3, 3], [null, null]] == result
    }

    @Test
    void testRightJoin3() {
        def nums2 = [1, 2, 3, null]
        def nums1 = [2, 3, 4, null]
        def result = from(nums1).rightJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[null, 1], [2, 2], [3, 3], [null, null]] == result
    }

    @Test
    void testLeftJoin4() {
        def nums1 = [1, 2, 3]
        def nums2 = [2, 3, 4, null]
        def result = from(nums1).leftJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[1, null], [2, 2], [3, 3]] == result
    }

    @Test
    void testRightJoin4() {
        def nums2 = [1, 2, 3]
        def nums1 = [2, 3, 4, null]
        def result = from(nums1).rightJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[null, 1], [2, 2], [3, 3]] == result
    }

    @Test
    void testLeftJoin5() {
        def nums1 = [1, 2, 3, null, null]
        def nums2 = [2, 3, 4]
        def result = from(nums1).leftJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[1, null], [2, 2], [3, 3], [null, null], [null, null]] == result
    }

    @Test
    void testRightJoin5() {
        def nums2 = [1, 2, 3, null, null]
        def nums1 = [2, 3, 4]
        def result = from(nums1).rightJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[null, 1], [2, 2], [3, 3], [null, null], [null, null]] == result
    }

    @Test
    void testLeftJoin6() {
        def nums1 = [1, 2, 3, null, null]
        def nums2 = [2, 3, 4, null]
        def result = from(nums1).leftJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[1, null], [2, 2], [3, 3], [null, null], [null, null]] == result
    }

    @Test
    void testRightJoin6() {
        def nums2 = [1, 2, 3, null, null]
        def nums1 = [2, 3, 4, null]
        def result = from(nums1).rightJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[null, 1], [2, 2], [3, 3], [null, null], [null, null]] == result
    }

    @Test
    void testLeftJoin7() {
        def nums1 = [1, 2, 3, null, null]
        def nums2 = [2, 3, 4, null, null]
        def result = from(nums1).leftJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[1, null], [2, 2], [3, 3], [null, null], [null, null]] == result
    }

    @Test
    void testRightJoin7() {
        def nums2 = [1, 2, 3, null, null]
        def nums1 = [2, 3, 4, null, null]
        def result = from(nums1).rightJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[null, 1], [2, 2], [3, 3], [null, null], [null, null]] == result
    }

    @Test
    void testLeftJoin8() {
        def nums1 = [1, 2, 3, null]
        def nums2 = [2, 3, 4, null, null]
        def result = from(nums1).leftJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[1, null], [2, 2], [3, 3], [null, null]] == result
    }

    @Test
    void testRightJoin8() {
        def nums2 = [1, 2, 3, null]
        def nums1 = [2, 3, 4, null, null]
        def result = from(nums1).rightJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[null, 1], [2, 2], [3, 3], [null, null]] == result
    }

    @Test
    void testLeftJoin9() {
        def nums1 = [1, 2, 3]
        def nums2 = [2, 3, 4, null, null]
        def result = from(nums1).leftJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[1, null], [2, 2], [3, 3]] == result
    }

    @Test
    void testRightJoin9() {
        def nums2 = [1, 2, 3]
        def nums1 = [2, 3, 4, null, null]
        def result = from(nums1).rightJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[null, 1], [2, 2], [3, 3]] == result
    }

    @Test
    void testFullJoin() {
        def nums1 = [1, 2, 3]
        def nums2 = [2, 3, 4]
        def result = from(nums1).fullJoin(from(nums2), (a, b) -> a == b).toList()
        assert [[1, null], [2, 2], [3, 3], [null, 4]] == result
    }

    @Test
    void testCrossJoin() {
        def nums1 = [1, 2, 3]
        def nums2 = [3, 4, 5]
        def result = from(nums1).crossJoin(from(nums2)).toList()
        assert [[1, 3], [1, 4], [1, 5], [2, 3], [2, 4], [2, 5], [3, 3], [3, 4], [3, 5]] == result
    }

    @Test
    void testWhere() {
        def nums = [1, 2, 3, 4, 5]
        def result = from(nums).where(e -> e > 3).toList()
        assert [4, 5] == result
    }

    @Test
    void testGroupBySelect0() {
        def nums = [1, 2, 2, 3, 3, 4, 4, 5]
        def result = from(nums).groupBy(e -> e).select(e -> Tuple.tuple(e.v1, e.v2.toList())).toList()
        assert [[1, [1]], [2, [2, 2]], [3, [3, 3]], [4, [4, 4]], [5, [5]]] == result
    }

    @Test
    void testGroupBySelect1() {
        def nums = [1, 2, 2, 3, 3, 4, 4, 5]
        def result = from(nums).groupBy(e -> e).select(e -> Tuple.tuple(e.v1, e.v2.count())).toList()
        assert [[1, 1], [2, 2], [3, 2], [4, 2], [5, 1]] == result
    }

    @Test
    @CompileDynamic
    void testGroupBySelect2() {
        def nums = [1, 2, 2, 3, 3, 4, 4, 5]
        def result =
                from(nums).groupBy(e -> e)
                        .select(e ->
                                Tuple.tuple(
                                        e.v1,
                                        e.v2.count(),
                                        e.v2.sum(n -> new BigDecimal(n))
                                )
                        ).toList()
        assert [[1, 1, 1], [2, 2, 4], [3, 2, 6], [4, 2, 8], [5, 1, 5]] == result
    }

    @Test
    @CompileDynamic
    void testGroupBySelect3() {
        def nums = [1, 2, 2, 3, 3, 4, 4, 5]
        def result =
                from(nums).groupBy(e -> e, g -> g.v1 > 2)
                        .select(e ->
                                Tuple.tuple(
                                        e.v1,
                                        e.v2.count(),
                                        e.v2.sum(n -> new BigDecimal(n))
                                )
                        ).toList()
        assert [[3, 2, 6], [4, 2, 8], [5, 1, 5]] == result
    }

    @Test
    void testGroupBySelect4() {
        def persons = [new Person2('Linda', 100, 'Female'),
                       new Person2('Daniel', 135, 'Male'),
                       new Person2('David', 121, 'Male')]

        def result = from(persons).groupBy(p -> p.gender)
                .select(e -> Tuple.tuple(e.v1, e.v2.count())).toList()

        assert [['Male', 2], ['Female', 1]] == result
    }

    @Test
    @CompileDynamic
    void testGroupBySelect5() {
        def persons = [new Person2('Linda', 100, 'Female'),
                       new Person2('Daniel', 135, 'Male'),
                       new Person2('David', 121, 'Male')]

        def result = from(persons).groupBy(p -> new NamedTuple<>([p.gender], ['gender']))
                .select(e -> Tuple.tuple(e.v1.gender, e.v2.min(p -> p.weight), e.v2.max(p -> p.weight))).toList()

        assert [['Male', 121, 135], ['Female', 100, 100]] == result
    }

    @Test
    void testOrderBy() {
        Person daniel = new Person('Daniel', 35)
        Person peter = new Person('Peter', 10)
        Person alice = new Person('Alice', 22)
        Person john = new Person('John', 10)

        def persons = [daniel, peter, alice, john]
        def result = from(persons).orderBy(
                new Queryable.Order<Person, Comparable>((Person e) -> e.age, true),
                new Queryable.Order<Person, Comparable>((Person e) -> e.name, true)
        ).toList()
        assert [john, peter, alice, daniel] == result

        result = from(persons).orderBy(
                new Queryable.Order<Person, Comparable>((Person e) -> e.age, false),
                new Queryable.Order<Person, Comparable>((Person e) -> e.name, true)
        ).toList()
        assert [daniel, alice, john, peter] == result

        result = from(persons).orderBy(
                new Queryable.Order<Person, Comparable>((Person e) -> e.age, true),
                new Queryable.Order<Person, Comparable>((Person e) -> e.name, false)
        ).toList()
        assert [peter, john, alice, daniel] == result

        result = from(persons).orderBy(
                new Queryable.Order<Person, Comparable>((Person e) -> e.age, false),
                new Queryable.Order<Person, Comparable>((Person e) -> e.name, false)
        ).toList()
        assert [daniel, alice, peter, john] == result
    }

    @Test
    void testLimit() {
        def nums = [1, 2, 3, 4, 5]
        def result = from(nums).limit(1, 2).toList()
        assert [2, 3] == result

        result = from(nums).limit(2).toList()
        assert [1, 2] == result
    }

    @Test
    void testSelect() {
        def nums = [1, 2, 3, 4, 5]
        def result = from(nums).select(e -> e + 1).toList()
        assert [2, 3, 4, 5, 6] == result
    }

    @Test
    void testDistinct() {
        def nums = [1, 2, 2, 3, 3, 2, 3, 4, 5, 5]
        def result = from(nums).distinct().toList()
        assert [1, 2, 3, 4, 5] == result
    }

    @Test
    void testUnion() {
        def nums1 = [1, 2, 3]
        def nums2 = [2, 3, 4]
        def result = from(nums1).union(from(nums2)).toList()
        assert [1, 2, 3, 4] == result
    }

    @Test
    void testUnionAll() {
        def nums1 = [1, 2, 3]
        def nums2 = [2, 3, 4]
        def result = from(nums1).unionAll(from(nums2)).toList()
        assert [1, 2, 3, 2, 3, 4] == result
    }

    @Test
    void testIntersect() {
        def nums1 = [1, 2, 2, 3]
        def nums2 = [2, 3, 3, 4]
        def result = from(nums1).intersect(from(nums2)).toList()
        assert [2, 3] == result
    }

    @Test
    void testMinus() {
        def nums1 = [1, 1, 2, 3]
        def nums2 = [2, 3, 4]
        def result = from(nums1).minus(from(nums2)).toList()
        assert [1] == result
    }

    @Test
    void testFromWhereLimitSelect() {
        def nums1 = [1, 2, 3, 4, 5]
        def nums2 = [0, 1, 2, 3, 4, 5, 6]
        def result =
                from(nums1)
                        .innerJoin(from(nums2), (a, b) -> a == b)
                        .where(t -> t.v1 > 1)
                        .limit(1, 2)
                        .select(t -> t.v1 + 1)
                        .toList()
        assert [4, 5] == result
    }

    @Test
    void testStream() {
        def nums = [1, 2, 3]
        def result = from(nums).stream().collect(Collectors.toList())
        assert nums == result
    }

    @ToString
    @EqualsAndHashCode
    static class Person {
        String name
        int age

        Person(String name, int age) {
            this.name = name
            this.age = age
        }
    }

    @ToString
    @EqualsAndHashCode
    class Person2 {
        String name
        int weight
        String gender

        Person2(String name, int weight, String gender) {
            this.name = name
            this.weight = weight
            this.gender = gender
        }
    }
}
