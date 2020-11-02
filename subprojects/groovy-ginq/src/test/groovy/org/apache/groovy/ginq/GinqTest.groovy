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
package org.apache.groovy.ginq

import groovy.transform.CompileStatic
import org.junit.Test

import static groovy.test.GroovyAssert.assertScript

@CompileStatic
class GinqTest {
    @Test
    void "testGinq - from select - 0"() {
        assertScript '''
            assert [0, 1, 2] == GQ {
                from n in [0, 1, 2]
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - from select - 1"() {
        assertScript '''
            def numbers = [0, 1, 2]
            assert [0, 1, 2] == GQ {
                from n in numbers
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - from select - 2"() {
        assertScript '''
            def numbers = [0, 1, 2]
            assert [0, 2, 4] == GQ {
                from n in numbers
                select n * 2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from select - 3"() {
        assertScript '''
            class Person {
                String name
                int age
                
                Person(String name, int age) {
                    this.name = name
                    this.age = age
                }
            }

            def persons = [new Person('Daniel', 35), new Person('Linda', 21), new Person('Peter', 30)]
            assert [35, 21, 30] == GQ {
                from p in persons
                select p.age
            }.toList()
        '''
    }

    @Test
    void "testGinq - from select - 4"() {
        assertScript '''
            class Person {
                String name
                int age
                
                Person(String name, int age) {
                    this.name = name
                    this.age = age
                }
            }

            def persons = [new Person('Daniel', 35), new Person('Linda', 21), new Person('Peter', 30)]
            assert [['Daniel', 35], ['Linda', 21], ['Peter', 30]] == GQ {
                from p in persons
                select p.name, p.age
            }.toList()
        '''
    }

    @Test
    void "testGinq - from select - 5"() {
        assertScript '''
            class Person {
                String name
                int age
                
                Person(String name, int age) {
                    this.name = name
                    this.age = age
                }
            }

            def persons = [new Person('Daniel', 35), new Person('Linda', 21), new Person('Peter', 30)]
            assert [[name:'Daniel', age:35], [name:'Linda', age:21], [name:'Peter', age:30]] == GQ {
                from p in persons
                select (name: p.name, age: p.age)
            }.toList()
        '''
    }

    @Test
    void "testGinq - from select - 6"() {
        assertScript '''
            def numbers = [0, 1, 2]
            assert [0, 1, 2] == GQ {
                from n in numbers select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - from where select - 1"() {
        assertScript '''
            def numbers = [0, 1, 2, 3, 4, 5]
            assert [2, 4, 6] == GQ {
                from n in numbers
                where n > 0 && n <= 3
                select n * 2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from where select - 2"() {
        assertScript '''
            def numbers = [0, 1, 2, 3, 4, 5]
            assert [2, 4, 6] == GQ {
                from n in numbers where n > 0 && n <= 3 select n * 2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin select - 1"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [1, 2, 3]
            assert [[1, 1], [2, 2], [3, 3]] == GQ {
                from n1 in nums1
                innerjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin select - 2"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [1, 2, 3]
            assert [[2, 1], [3, 2], [4, 3]] == GQ {
                from n1 in nums1
                innerjoin n2 in nums2 on n1 == n2
                select n1 + 1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin select - 3"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [1, 2, 3]
            assert [[1, 2], [2, 3], [3, 4]] == GQ {
                from n1 in nums1
                innerjoin n2 in nums2 on n1 == n2
                select n1, n2 + 1
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin select - 4"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [1, 2, 3]
            assert [[1, 2], [2, 3]] == GQ {
                from n1 in nums1
                innerjoin n2 in nums2 on n1 + 1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin select - 5"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [1, 2, 3]
            assert [[1, 2], [2, 3]] == GQ {
                from n1 in nums1 innerjoin n2 in nums2 on n1 + 1 == n2 select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin select - 6"() {
        assertScript '''
            class Person {
                String name
                int age
                
                Person(String name, int age) {
                    this.name = name
                    this.age = age
                }
            }

            def persons1 = [new Person('Daniel', 35), new Person('Linda', 21), new Person('Peter', 30)]
            def persons2 = [new Person('Jack', 35), new Person('Rose', 21), new Person('Smith', 30)]
            assert [['Daniel', 'Jack'], ['Linda', 'Rose'], ['Peter', 'Smith']] == GQ {
                from p1 in persons1
                innerjoin p2 in persons2 on p1.age == p2.age
                select p1.name as p1Name, p2.name as p2Name
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin select - 7"() {
        assertScript '''
            class Person {
                String name
                int age
                
                Person(String name, int age) {
                    this.name = name
                    this.age = age
                }
            }
            
            def persons1 = [new Person('Daniel', 35), new Person('Linda', 21), new Person('Peter', 30)]
            def persons2 = [new Person('Jack', 35), new Person('Rose', 21), new Person('Smith', 30)]
            assert [['DANIEL', 'JACK'], ['LINDA', 'ROSE'], ['PETER', 'SMITH']] == GQ {
                from p1 in persons1
                innerjoin p2 in persons2 on p1.age == p2.age
                select p1.name.toUpperCase(), p2.name.toUpperCase()
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin select - 8"() {
        assertScript '''
            class Person {
                String name
                int age
                
                Person(String name, int age) {
                    this.name = name
                    this.age = age
                }
            }
            
            def same(str) { str }

            def persons1 = [new Person('Daniel', 35), new Person('Linda', 21), new Person('Peter', 30)]
            def persons2 = [new Person('Jack', 35), new Person('Rose', 21), new Person('Smith', 30)]
            assert [['DANIEL', 'JACK'], ['LINDA', 'ROSE'], ['PETER', 'SMITH']] == GQ {
                from p1 in persons1
                innerjoin p2 in persons2 on p1.age == p2.age
                select same(p1.name.toUpperCase()), same(p2.name.toUpperCase())
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin select - 9"() {
        assertScript '''
            assert [1, 2, 3] == GQ {
                from n in [1, 2, 3]
                innerjoin k in [2, 3, 4] on n + 1 == k
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin select - 10"() {
        assertScript '''
            assert [2, 3, 4] == GQ {
                from n in [1, 2, 3]
                innerjoin k in [2, 3, 4] on n + 1 == k
                select k
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin select - 11"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [2, 3, 4]
            def nums3 = [3, 4, 5]
            assert [[3, 3, 3]] == GQ {
                from n1 in nums1
                innerjoin n2 in nums2 on n2 == n1
                innerjoin n3 in nums3 on n3 == n2
                select n1, n2, n3
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin select - 12"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [2, 3, 4]
            def nums3 = [3, 4, 5]
            assert [[3, 3, 3]] == GQ {
                from n1 in nums1
                innerjoin n2 in nums2 on n2 == n1
                innerjoin n3 in nums3 on n3 == n1
                select n1, n2, n3
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin select - 13"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [2, 3, 4]
            def nums3 = [3, 4, 5]
            assert [[3, 3, 3]] == GQ {
                from v in (
                    from n1 in nums1
                    innerjoin n2 in nums2 on n1 == n2
                    select n1, n2
                )
                innerjoin n3 in nums3 on v.n2 == n3
                select v.n1, v.n2, n3
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin where select - 1"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [1, 2, 3]
            assert [[2, 2], [3, 3]] == GQ {
                from n1 in nums1
                innerjoin n2 in nums2 on n1 == n2
                where n1 > 1 && n2 <= 3
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin where select - 2"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [1, 2, 3]
            assert [[2, 2], [3, 3]] == GQ {
                from n1 in nums1
                innerjoin n2 in nums2 on n1 == n2
                where Math.pow(n1, 1) > 1 && Math.pow(n2, 1) <= 3
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin where select - 3"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [1, 2, 3]
            assert [[2, 2], [3, 3]] == GQ {
                from n1 in nums1 innerjoin n2 in nums2 on n1 == n2 where Math.pow(n1, 1) > 1 && Math.pow(n2, 1) <= 3 select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin where select - 4"() {
        assertScript '''
            class Person {
                String name
                int age
                
                Person(String name, int age) {
                    this.name = name
                    this.age = age
                }
            }

            def persons1 = [new Person('Daniel', 35), new Person('Linda', 21), new Person('David', 30)]
            def persons2 = [new Person('Jack', 35), new Person('Rose', 21), new Person('Smith', 30)]
            assert [['Daniel', 'Jack']] == GQ {
                from p1 in persons1
                innerjoin p2 in persons2 on p1.age == p2.age
                where p1.name.startsWith('D') && p2.name.endsWith('k')
                select p1.name as p1Name, p2.name as p2Name
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin where select - 5"() {
        assertScript '''
            class Person {
                String name
                int age
                
                Person(String name, int age) {
                    this.name = name
                    this.age = age
                }
            }
            
            def same(obj) {obj}

            def persons1 = [new Person('Daniel', 35), new Person('Linda', 21), new Person('David', 30)]
            def persons2 = [new Person('Jack', 35), new Person('Rose', 21), new Person('Smith', 30)]
            assert [['Daniel', 'Jack']] == GQ {
                from p1 in persons1
                innerjoin p2 in persons2 on p1.age == p2.age
                where same(p1.name.startsWith('D')) && same(p2.name.endsWith('k'))
                select p1.name as p1Name, p2.name as p2Name
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 0"() {
        assertScript '''
            assert [1, 2, 3] == GQ {
                from v in (
                    from n in [1, 2, 3]
                    select n
                )
                select v
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 1"() {
        assertScript '''
            def numbers = [1, 2, 3]
            assert [1, 2, 3] == GQ {
                from v in (
                    from n in numbers
                    select n
                )
                select v
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 2"() {
        assertScript '''
            def numbers = [1, 2, 3]
            assert [1, 2] == GQ {
                from v in (
                    from n in numbers
                    where n < 3
                    select n
                )
                select v
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 3"() {
        assertScript '''
            def numbers = [1, 2, 3]
            assert [2] == GQ {
                from v in (
                    from n in numbers
                    where n < 3
                    select n
                )
                where v > 1
                select v
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 4"() {
        assertScript '''
            def nums1 = [1, 2, 3, 4, 5]
            def nums2 = [1, 2, 3, 4, 5]
            assert [[3, 3], [5, 5]] == GQ {
                from v in (
                    from n1 in nums1
                    innerjoin n2 in nums2 on n1 == n2
                    where n1 > 1 && n2 <= 5
                    select n1, n2
                )
                where v.n1 >= 3 && v.n2 in [3, 5]
                select v
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 5"() {
        assertScript '''
            def nums1 = [1, 2, 3, 4, 5]
            def nums2 = [1, 2, 3, 4, 5]
            assert [[3, 3], [5, 5]] == GQ {
                from v in (
                    from n1 in nums1
                    innerjoin n2 in nums2 on n1 == n2
                    where n1 > 1 && n2 <= 5
                    select n1, n2
                )
                where v['n1'] >= 3 && v['n2'] in [3, 5]
                select v
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 6"() {
        assertScript '''
            def nums1 = [1, 2, 3, 4, 5]
            def nums2 = [1, 2, 3, 4, 5]
            assert [[3, 3], [5, 5]] == GQ {
                from v in (
                    from n1 in nums1
                    innerjoin n2 in nums2 on n1 == n2
                    where n1 > 1 && n2 <= 5
                    select n1, n2
                )
                where v[0] >= 3 && v[1] in [3, 5] // v[0] references column1 n1, and v[1] references column2 n2
                select v
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 7"() {
        assertScript '''
            def nums1 = [1, 2, 3, 4, 5]
            def nums2 = [1, 2, 3, 4, 5]
            assert [[3, 3], [5, 5]] == GQ {
                from v in (
                    from n1 in nums1
                    innerjoin n2 in nums2 on n1 == n2
                    where n1 > 1 && n2 <= 5
                    select n1 as vn1, n2 as vn2 // rename column names
                )
                where v.vn1 >= 3 && v.vn2 in [3, 5]
                select v
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 8"() {
        assertScript '''
            def nums1 = [1, 2, 3, 4, 5]
            def nums2 = [1, 2, 3, 4, 5]
            assert [[3, 3], [5, 5]] == GQ {
                from v in (
                    from n1 in nums1
                    innerjoin n2 in nums2 on n1 == n2
                    where n1 > 1 && n2 <= 5
                    select ((n1 as Integer) as vn1), ((n2 as Integer) as vn2)
                )
                where v.vn1 >= 3 && v.vn2 in [3, 5]
                select v
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 9"() {
        assertScript '''
            assert [2, 6] == GQ {
                from v in (
                    from n in (
                        from m in [1, 2, 3]
                        select m as v1, (m + 1) as v2
                    )
                    where n.v2 < 4
                    select n.v1 * n.v2
                )
                select v
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 10"() {
        assertScript '''
            assert [2, 6] == GQ {
                from v in (
                    from n in (
                        from m in [1, 2, 3]
                        select m, (m + 1) as v2
                    )
                    where n.v2 < 4
                    select n.m * n.v2
                )
                select v
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 11"() {
        assertScript '''
            assert [[1, 2], [2, 3]] == GQ {
                from v in (
                    from n in (
                        from m in [1, 2, 3]
                        select m, (m + 1) as v2
                    )
                    where n.v2 < 4
                    select n.m, n.v2   // its column names are: m, v2
                )
                select v.m, v.v2
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 12"() {
        assertScript '''
            assert [[1, 2], [2, 3]] == GQ {
                from v in (
                    from n in (
                        from m in [1, 2, 3]
                        select m, (m + 1) as v2
                    )
                    where n.v2 < 4
                    select n.m, n.v2
                )
                select v."${'m'}", v.v2   // dynamic column name
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 13"() {
        assertScript '''
            assert [2, 6] == GQ {
                from v in (
                    from n in (
                        from m in [1, 2, 3]
                        select m as v1, (m + 1) as v2
                    )
                    innerjoin k in [2, 3, 4] on n.v2 == k
                    where n.v2 < 4
                    select n.v1 * k
                )
                select v
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 14"() {
        assertScript '''
            assert [2, 3] == GQ {
                from n in [1, 2, 3]
                innerjoin k in (
                    from m in [2, 3, 4]
                    select m
                ) on n == k
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 15"() {
        assertScript '''
            assert [1, 2] == GQ {
                from n in [0, 1, 2]
                where n in (
                    from m in [1, 2]
                    select m
                )
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 16"() {
        assertScript '''
            assert [[2, 2]] == GQ {
                from t in [[0, 0], [1, 1], [2, 2]]
                where t in (
                    from m in [1, 2]
                    innerjoin k in [2, 3] on k == m
                    select m, k
                )
                select t
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 17"() {
        assertScript '''
            import static groovy.lang.Tuple.*
            
            @groovy.transform.EqualsAndHashCode
            class Person {
                String firstName
                String lastName
                int age
                String gender
                
                Person(String firstName, String lastName, int age, String gender) {
                    this.firstName = firstName
                    this.lastName = lastName
                    this.age = age
                    this.gender = gender
                }
            }
            @groovy.transform.EqualsAndHashCode
            class LuckyInfo {
                String lastName
                String gender
                boolean valid
                
                LuckyInfo(String lastName, String gender, boolean valid) {
                    this.lastName = lastName
                    this.gender = gender
                    this.valid = valid
                }
            }
            
            def persons = [new Person('Daniel', 'Sun', 35, 'Male'), new Person('Linda', 'Yang', 21, 'Female'), 
                          new Person('Peter', 'Yang', 30, 'Male'), new Person('Rose', 'Yang', 30, 'Female')]
            def luckyInfoList = [new LuckyInfo('Sun', 'Male', true), new LuckyInfo('Yang', 'Female', true), 
                                 new LuckyInfo('Yang', 'Male', false)]        

            assert ['Daniel', 'Linda', 'Rose'] == GQ {
                from p in persons
                where tuple(p.lastName, p.gender) in (
                    from luckyInfo in luckyInfoList
                    where luckyInfo.valid == true
                    select luckyInfo.lastName, luckyInfo.gender
                )
                select p.firstName
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 18"() {
        assertScript '''
            assert [1, 2] == GQ {
                from n in (
                    from m in [0, 1, 2]
                    select m
                )
                where n in (
                    from m in [1, 2]
                    select m
                )
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 19"() {
        assertScript '''
            assert [2] == GQ {
                from n in (
                    from m in [0, 1, 2]
                    where m > 0
                    select m
                )
                where n in (
                    from m in [1, 2]
                    where m > 1
                    select m
                )
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 20"() {
        assertScript '''
            assert [[1, 1], [2, 4], [3, 9]] == GQ {
                from v in (
                    from n in [1, 2, 3]
                    select n, Math.pow(n, 2) as powerOfN
                )
                select v.n, v.powerOfN
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 21"() {
        assertScript '''
            assert [2] == GQ {
                from n in [0, 1, 2]
                where n > 1 && n in (
                    from m in [1, 2]
                    select m
                )
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - nested from - 22"() {
        assertScript '''
            assert [2] == GQ {
                from n in [0, 1, 2]
                where n in (
                    from m in [1, 2]
                    select m
                ) && n > 1
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - from leftjoin select - 1"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [1, 2, 3]
            assert [[1, 1], [2, 2], [3, 3]] == GQ {
                from n1 in nums1
                leftjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from leftjoin select - 2"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [2, 3, 4]
            assert [[1, null], [2, 2], [3, 3]] == GQ {
                from n1 in nums1
                leftjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from leftjoin select - 3"() {
        assertScript '''
            def nums1 = [1, 2, 3, null]
            def nums2 = [2, 3, 4]
            assert [[1, null], [2, 2], [3, 3], [null, null]] == GQ {
                from n1 in nums1
                leftjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from leftjoin select - 4"() {
        assertScript '''
            def nums1 = [1, 2, 3, null]
            def nums2 = [2, 3, 4, null]
            assert [[1, null], [2, 2], [3, 3], [null, null]] == GQ {
                from n1 in nums1
                leftjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from leftjoin select - 5"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [2, 3, 4, null]
            assert [[1, null], [2, 2], [3, 3]] == GQ {
                from n1 in nums1
                leftjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from leftjoin select - 6"() {
        assertScript '''
            def nums1 = [1, 2, 3, null, null]
            def nums2 = [2, 3, 4]
            assert [[1, null], [2, 2], [3, 3], [null, null], [null, null]] == GQ {
                from n1 in nums1
                leftjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from leftjoin select - 7"() {
        assertScript '''
            def nums1 = [1, 2, 3, null, null]
            def nums2 = [2, 3, 4, null]
            assert [[1, null], [2, 2], [3, 3], [null, null], [null, null]] == GQ {
                from n1 in nums1
                leftjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from leftjoin select - 8"() {
        assertScript '''
            def nums1 = [1, 2, 3, null, null]
            def nums2 = [2, 3, 4, null, null]
            assert [[1, null], [2, 2], [3, 3], [null, null], [null, null]] == GQ {
                from n1 in nums1
                leftjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from leftjoin select - 9"() {
        assertScript '''
            def nums1 = [1, 2, 3, null]
            def nums2 = [2, 3, 4, null, null]
            assert [[1, null], [2, 2], [3, 3], [null, null]] == GQ {
                from n1 in nums1
                leftjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from leftjoin select - 10"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [2, 3, 4, null, null]
            assert [[1, null], [2, 2], [3, 3]] == GQ {
                from n1 in nums1
                leftjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from leftjoin where select - 1"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [2, 3, 4, null, null]
            assert [[2, 2], [3, 3]] == GQ {
                from n1 in nums1
                leftjoin n2 in nums2 on n1 == n2
                where n2 != null
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from rightjoin select - 1"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [1, 2, 3]
            assert [[1, 1], [2, 2], [3, 3]] == GQ {
                from n1 in nums1
                rightjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from rightjoin select - 2"() {
        assertScript '''
            def nums2 = [1, 2, 3]
            def nums1 = [2, 3, 4]
            assert [[null, 1], [2, 2], [3, 3]] == GQ {
                from n1 in nums1
                rightjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from rightjoin select - 3"() {
        assertScript '''
            def nums2 = [1, 2, 3, null]
            def nums1 = [2, 3, 4]
            assert [[null, 1], [2, 2], [3, 3], [null, null]] == GQ {
                from n1 in nums1
                rightjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from rightjoin select - 4"() {
        assertScript '''
            def nums2 = [1, 2, 3, null]
            def nums1 = [2, 3, 4, null]
            assert [[null, 1], [2, 2], [3, 3], [null, null]] == GQ {
                from n1 in nums1
                rightjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from rightjoin select - 5"() {
        assertScript '''
            def nums2 = [1, 2, 3]
            def nums1 = [2, 3, 4, null]
            assert [[null, 1], [2, 2], [3, 3]] == GQ {
                from n1 in nums1
                rightjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from rightjoin select - 6"() {
        assertScript '''
            def nums2 = [1, 2, 3, null, null]
            def nums1 = [2, 3, 4]
            assert [[null, 1], [2, 2], [3, 3], [null, null], [null, null]] == GQ {
                from n1 in nums1
                rightjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from rightjoin select - 7"() {
        assertScript '''
            def nums2 = [1, 2, 3, null, null]
            def nums1 = [2, 3, 4, null]
            assert [[null, 1], [2, 2], [3, 3], [null, null], [null, null]] == GQ {
                from n1 in nums1
                rightjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from rightjoin select - 8"() {
        assertScript '''
            def nums2 = [1, 2, 3, null, null]
            def nums1 = [2, 3, 4, null, null]
            assert [[null, 1], [2, 2], [3, 3], [null, null], [null, null]] == GQ {
                from n1 in nums1
                rightjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from rightjoin select - 9"() {
        assertScript '''
            def nums2 = [1, 2, 3, null]
            def nums1 = [2, 3, 4, null, null]
            assert [[null, 1], [2, 2], [3, 3], [null, null]] == GQ {
                from n1 in nums1
                rightjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from rightjoin select - 10"() {
        assertScript '''
            def nums2 = [1, 2, 3]
            def nums1 = [2, 3, 4, null, null]
            assert [[null, 1], [2, 2], [3, 3]] == GQ {
                from n1 in nums1
                rightjoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from rightjoin where select - 1"() {
        assertScript '''
            def nums2 = [1, 2, 3]
            def nums1 = [2, 3, 4, null, null]
            assert [[2, 2], [3, 3]] == GQ {
                from n1 in nums1
                rightjoin n2 in nums2 on n1 == n2
                where n1 != null
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from fulljoin select - 1"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [2, 3, 4]
            assert [[1, null], [2, 2], [3, 3], [null, 4]] == GQ {
                from n1 in nums1
                fulljoin n2 in nums2 on n1 == n2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from fulljoin where select - 1"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [2, 3, 4]
            assert [[2, 2], [3, 3]] == GQ {
                from n1 in nums1
                fulljoin n2 in nums2 on n1 == n2
                where n1 != null && n2 != null 
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from crossjoin select - 1"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [3, 4, 5]
            assert [[1, 3], [1, 4], [1, 5], [2, 3], [2, 4], [2, 5], [3, 3], [3, 4], [3, 5]] == GQ {
                from n1 in nums1
                crossjoin n2 in nums2
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from crossjoin where select - 1"() {
        assertScript '''
            def nums1 = [1, 2, 3]
            def nums2 = [3, 4, 5]
            assert [[3, 3], [3, 5]] == GQ {
                from n1 in nums1
                crossjoin n2 in nums2
                where n1 > 2 && n2 != 4
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from orderby select - 1"() {
        assertScript '''
            assert [1, 2, 5, 6] == GQ {
                from n in [1, 5, 2, 6]
                orderby n
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - from orderby select - 2"() {
        assertScript '''
            @groovy.transform.EqualsAndHashCode
            class Person {
                String name
                int age
                
                Person(String name, int age) {
                    this.name = name
                    this.age = age
                }
            }
            def persons = [new Person('Linda', 21), new Person('Daniel', 35), new Person('David', 21)]
            assert [new Person('Daniel', 35), new Person('Linda', 21), new Person('David', 21)] == GQ {
                from p in persons
                orderby p.age in desc
                select p
            }.toList()
        '''
    }

    @Test
    void "testGinq - from orderby select - 3"() {
        assertScript '''
            @groovy.transform.EqualsAndHashCode
            class Person {
                String name
                int age
                
                Person(String name, int age) {
                    this.name = name
                    this.age = age
                }
            }
            def persons = [new Person('Linda', 21), new Person('Daniel', 35), new Person('David', 21)]
            assert [new Person('Daniel', 35), new Person('David', 21), new Person('Linda', 21)] == GQ {
                from p in persons
                orderby p.age in desc, p.name
                select p
            }.toList()
        '''
    }

    @Test
    void "testGinq - from orderby select - 4"() {
        assertScript '''
            @groovy.transform.EqualsAndHashCode
            class Person {
                String name
                int age
                
                Person(String name, int age) {
                    this.name = name
                    this.age = age
                }
            }
            def persons = [new Person('Linda', 21), new Person('Daniel', 35), new Person('David', 21)]
            assert [new Person('Daniel', 35), new Person('David', 21), new Person('Linda', 21)] == GQ {
                from p in persons
                orderby p.age in desc, p.name in asc
                select p
            }.toList()
        '''
    }

    @Test
    void "testGinq - from orderby select - 5"() {
        assertScript '''
            @groovy.transform.EqualsAndHashCode
            class Person {
                String name
                int age
                
                Person(String name, int age) {
                    this.name = name
                    this.age = age
                }
            }
            def persons = [new Person('Linda', 21), new Person('Daniel', 35), new Person('David', 21)]
            assert [new Person('Daniel', 35), new Person('Linda', 21), new Person('David', 21)] == GQ {
                from p in persons
                orderby p.age in desc, p.name in desc
                select p
            }.toList()
        '''
    }

    @Test
    void "testGinq - from orderby select - 6"() {
        assertScript '''
            @groovy.transform.EqualsAndHashCode
            class Person {
                String name
                int age
                
                Person(String name, int age) {
                    this.name = name
                    this.age = age
                }
            }
            def persons = [new Person('Linda', 21), new Person('Daniel', 35), new Person('David', 21)]
            assert [new Person('Linda', 21), new Person('David', 21), new Person('Daniel', 35)] == GQ {
                from p in persons
                orderby p.age, p.name in desc
                select p
            }.toList()
        '''
    }

    @Test
    void "testGinq - from orderby select - 7"() {
        assertScript '''
            @groovy.transform.EqualsAndHashCode
            class Person {
                String name
                int age
                
                Person(String name, int age) {
                    this.name = name
                    this.age = age
                }
            }
            def persons = [new Person('Linda', 21), new Person('Daniel', 35), new Person('David', 21)]
            assert [new Person('Linda', 21), new Person('David', 21), new Person('Daniel', 35)] == GQ {
                from p in persons
                orderby p.age in asc, p.name in desc
                select p
            }.toList()
        '''
    }

    @Test
    void "testGinq - from orderby select - 8"() {
        assertScript '''
            assert [1, 2, 5, 6] == GQ {
                from n in [1, 5, 2, 6]
                orderby 1 / n in desc
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - from orderby select - 9"() {
        assertScript '''
            assert [1, 2, 5, 6] == GQ {
                from n in [1, 5, 2, 6]
                orderby Math.pow(1 / n, 1) in desc
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin orderby select - 1"() {
        assertScript '''
            assert [2, 3] == GQ {
                from n1 in [1, 2, 3]
                innerjoin n2 in [2, 3, 4] on n1 == n2
                orderby n1
                select n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin orderby select - 2"() {
        assertScript '''
            assert [3, 2] == GQ {
                from n1 in [1, 2, 3]
                innerjoin n2 in [2, 3, 4] on n1 == n2
                orderby n1 in desc
                select n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin orderby select - 3"() {
        assertScript '''
            assert [[3, 3], [2, 2]] == GQ {
                from n1 in [1, 2, 3]
                innerjoin n2 in [2, 3, 4] on n1 == n2
                orderby n1 in desc, n2 in desc
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from limit select - 1"() {
        assertScript '''
            assert [1, 2, 3] == GQ {
                from n in [1, 2, 3, 4, 5]
                limit 3
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - from limit select - 2"() {
        assertScript '''
            assert [2, 3, 4] == GQ {
                from n in [1, 2, 3, 4, 5]
                limit 1, 3
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - from orderby limit select - 3"() {
        assertScript '''
            assert [5, 4, 3] == GQ {
                from n in [1, 2, 3, 4, 5]
                orderby n in desc
                limit 3
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - from orderby limit select - 4"() {
        assertScript '''
            assert [4, 3, 2] == GQ {
                from n in [1, 2, 3, 4, 5]
                orderby n in desc
                limit 1, 3
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - from where orderby limit select - 1"() {
        assertScript '''
            assert [5, 4, 3] == GQ {
                from n in [1, 2, 3, 4, 5]
                where n >= 2
                orderby n in desc
                limit 3
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - from where orderby limit select - 2"() {
        assertScript '''
            assert [4, 3, 2] == GQ {
                from n in [1, 2, 3, 4, 5]
                where n >= 2
                orderby n in desc
                limit 1, 3
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - from where orderby limit select - 3"() {
        assertScript '''
            assert [5, 4] == GQ {
                from n in [1, 2, 3, 4, 5]
                where n > 3
                orderby n in desc
                limit 3
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - from where orderby limit select - 4"() {
        assertScript '''
            assert [4] == GQ {
                from n in [1, 2, 3, 4, 5]
                where n > 3
                orderby n in desc
                limit 1, 3
                select n
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin where orderby limit select - 1"() {
        assertScript '''
            assert [[4, 4], [3, 3]] == GQ {
                from n1 in [1, 2, 3, 4, 5]
                innerjoin n2 in [2, 3, 4, 5, 6] on n2 == n1
                where n1 > 2
                orderby n2 in desc
                limit 1, 3
                select n1, n2
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin where orderby limit select - 2"() {
        assertScript '''
            assert [[4, 4, 4]] == GQ {
                from n1 in [1, 2, 3, 4, 5]
                innerjoin n2 in [2, 3, 4, 5, 6] on n2 == n1
                innerjoin n3 in [3, 4, 5, 6, 7] on n3 == n2
                where n1 > 3
                orderby n2 in desc
                limit 1, 3
                select n1, n2, n3
            }.toList()
        '''
    }

    @Test
    void "testGinq - from groupby select - 1"() {
        assertScript '''
            assert [[1, 2], [3, 2], [6, 3]] == GQ {
                from n in [1, 1, 3, 3, 6, 6, 6]
                groupby n
                select n, count(n) // reference the column `n` in the groupby clause, and `count` is a built-in aggregate function
            }.toList()
        '''
    }

    @Test
    void "testGinq - from groupby select - 2"() {
        assertScript '''
            assert [[1, 2], [3, 6], [6, 18]] == GQ {
                from n in [1, 1, 3, 3, 6, 6, 6]
                groupby n
                select n, sum(n)
            }.toList()
        '''
    }

    @Test
    void "testGinq - from groupby select - 3"() {
        assertScript '''
            @groovy.transform.EqualsAndHashCode
            class Person {
                String name
                int weight
                String gender
                
                Person(String name, int weight, String gender) {
                    this.name = name
                    this.weight = weight
                    this.gender = gender
                }
            }
            def persons = [new Person('Linda', 100, 'Female'), new Person('Daniel', 135, 'Male'), new Person('David', 121, 'Male')]
            assert [['Female', 1], ['Male', 2]] == GQ {
                from p in persons
                groupby p.gender
                orderby count()
                select p.gender, count()
            }.toList()
        '''
    }

    @Test
    void "testGinq - from groupby select - 4"() {
        assertScript '''
            @groovy.transform.EqualsAndHashCode
            class Person {
                String name
                int weight
                String gender
                
                Person(String name, int weight, String gender) {
                    this.name = name
                    this.weight = weight
                    this.gender = gender
                }
            }
            def persons = [new Person('Linda', 100, 'Female'), new Person('Daniel', 135, 'Male'), new Person('David', 121, 'Male')]
            assert [['Female', 1], ['Male', 2]] == GQ {
                from p in persons
                groupby p.gender
                orderby count(p)
                select p.gender, count(p)
            }.toList()
        '''
    }

    @Test
    void "testGinq - from groupby select - 5"() {
        assertScript '''
            @groovy.transform.EqualsAndHashCode
            class Person {
                String name
                int weight
                String gender
                
                Person(String name, int weight, String gender) {
                    this.name = name
                    this.weight = weight
                    this.gender = gender
                }
            }
            def persons = [new Person('Linda', 100, 'Female'), new Person('Daniel', 135, 'Male'), new Person('David', 121, 'Male')]
            assert [['Female', 1], ['Male', 2]] == GQ {
                from p in persons
                groupby p.gender
                orderby count(p.gender)
                select p.gender, count(p.gender)
            }.toList()
        '''
    }

    @Test
    void "testGinq - from groupby select - 6"() {
        assertScript '''
            @groovy.transform.EqualsAndHashCode
            class Person {
                String name
                int weight
                String gender
                
                Person(String name, int weight, String gender) {
                    this.name = name
                    this.weight = weight
                    this.gender = gender
                }
            }
            def persons = [new Person('Linda', 100, 'Female'), new Person('Daniel', 135, 'Male'), new Person('David', 121, 'Male')]
            assert [['Female', 1], ['Male', 2]] == GQ {
                from p in persons
                groupby p.gender
                orderby count(p.name)
                select p.gender, count(p.name)
            }.toList()
        '''
    }

    @Test
    void "testGinq - from groupby select - 7"() {
        assertScript '''
            assert [[1, 2], [3, 6], [6, 18]] == GQ {
                from n in [1, 1, 3, 3, 6, 6, 6]
                groupby n
                select n, agg(_g.stream().map(e -> e).reduce(BigDecimal.ZERO, BigDecimal::add)) // the most powerful aggregate function, `_g` represents the grouped Queryable object
            }.toList()
        '''
    }

    @Test
    void "testGinq - from groupby select - 8"() {
        assertScript '''
            @groovy.transform.EqualsAndHashCode
            class Person {
                String firstName
                String lastName
                int age
                String gender
                
                Person(String firstName, String lastName, int age, String gender) {
                    this.firstName = firstName
                    this.lastName = lastName
                    this.age = age
                    this.gender = gender
                }
            }
            
            def persons = [new Person('Daniel', 'Sun', 35, 'Male'), new Person('Linda', 'Yang', 21, 'Female'), 
                          new Person('Peter', 'Yang', 30, 'Male'), new Person('Rose', 'Yang', 30, 'Female')]
                          
            assert [['Male', 30, 35], ['Female', 21, 30]] == GQ {
                from p in persons
                groupby p.gender
                select p.gender, min(p.age), max(p.age)
            }.toList()
        '''
    }

    @Test
    void "testGinq - from groupby select - 9"() {
        assertScript '''
            assert [1, 2, 3] == GQ {
                from s in ['a', 'bc', 'def']
                groupby s.size()
                select s.size()
            }.toList()
        '''
    }

    @Test
    void "testGinq - from groupby select - 10"() {
        assertScript '''
            assert [[3, 3], [2, 2]] == GQ {
                from n in [1, 2, 3]
                innerjoin s in ['bc', 'def'] on n == s.length()
                groupby n, s.length()
                select n, s.length()
            }.toList()
        '''
    }

    @Test
    void "testGinq - from groupby select - 11"() {
        assertScript '''
            assert [[2, 2, 2], [3, 3, 3]] == GQ {
                from n in [1, 2, 3]
                innerjoin m in [1, 2, 3] on m == n
                innerjoin s in ['bc', 'def'] on n == s.length()
                groupby n, m, s.length()
                select n, m, s.length()
            }.toList()
        '''
    }

    @Test
    void "testGinq - from where groupby select - 1"() {
        assertScript '''
            assert [[1, 2], [6, 3]] == GQ {
                from n in [1, 1, 3, 3, 6, 6, 6]
                where n != 3
                groupby n
                select n, count(n)
            }.toList()
        '''
    }

    @Test
    void "testGinq - from where groupby orderby select - 1"() {
        assertScript '''
            assert [[6, 3], [1, 2]] == GQ {
                from n in [1, 1, 3, 3, 6, 6, 6]
                where n != 3
                groupby n
                orderby n in desc
                select n, count(n)
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin where groupby orderby select - 1"() {
        assertScript '''
            assert [[6, 9], [1, 4]] == GQ {
                from n in [1, 1, 3, 3, 6, 6, 6]
                innerjoin m in [1, 1, 3, 3, 6, 6, 6] on n == m
                where n != 3
                groupby n
                orderby n in desc
                select n, count(n)
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin where groupby orderby select - 2"() {
        assertScript '''
            assert [[1, 4], [6, 9]] == GQ {
                from n in [1, 1, 3, 3, 6, 6, 6]
                innerjoin m in [1, 1, 3, 3, 6, 6, 6] on n == m
                where n != 3
                groupby n
                orderby count(n) in asc
                select n, count(n)
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin where groupby orderby select - 3"() {
        assertScript '''
            assert [[2, 3, 1], [1, 2, 1]] == GQ {
                from n in [1, 2, 3]
                innerjoin m in [2, 3, 4] on n + 1 == m
                where n != 3
                groupby n, m
                orderby n in desc
                select n, m, count(n)
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin where groupby orderby select - 4"() {
        assertScript '''
            assert [[1, 2, 1], [2, 3, 1]] == GQ {
                from n in [1, 2, 3]
                innerjoin m in [2, 3, 4] on n + 1 == m
                where n != 3
                groupby n, m
                orderby m in asc
                select n, m, count(n)
            }.toList()
        '''
    }

    @Test
    void "testGinq - from groupby having select - 1"() {
        assertScript '''
            assert [[3, 2], [6, 3]] == GQ {
                from n in [1, 1, 3, 3, 6, 6, 6]
                groupby n
                having n >= 3
                select n, count(n)
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin where groupby having orderby select - 1"() {
        assertScript '''
            assert [[1, 2, 1]] == GQ {
                from n in [1, 2, 3]
                innerjoin m in [2, 3, 4] on n + 1 == m
                where n != 3
                groupby n, m
                having n >= 1 && m < 3
                orderby m in asc
                select n, m, count(n)
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin where groupby having orderby select - 2"() {
        assertScript '''
            assert [[6, 9]] == GQ {
                from n in [1, 1, 3, 3, 6, 6, 6]
                innerjoin m in [1, 1, 3, 3, 6, 6, 6] on n == m
                where n != 3
                groupby n
                having count(m) > 4
                orderby count(n) in asc
                select n, count(n)
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin where groupby having orderby select - 3"() {
        assertScript '''
            assert [[6, 9]] == GQ {
                from n in [1, 1, 3, 3, 6, 6, 6]
                innerjoin m in [1, 1, 3, 3, 6, 6, 6] on n == m
                where n != 3
                groupby n
                having count(n) > 4
                orderby count(n) in asc
                select n, count(n)
            }.toList()
        '''
    }

    @Test
    void "testGinq - from innerjoin where groupby having orderby select - 4"() {
        assertScript '''
            assert [[6, 9]] == GQ {
                from n in [1, 1, 3, 3, 6, 6, 6]
                innerjoin m in [1, 1, 3, 3, 6, 6, 6] on n == m
                where n != 3
                groupby n
                having count() > 4
                orderby count(n) in asc
                select n, count(n)
            }.toList()
        '''
    }

    @Test
    void "testGinq - query json - 1"() {
        assertScript """
            import groovy.json.JsonSlurper
            def parser = new JsonSlurper()
            def json = parser.parseText('''
                {
                    "persons": [
                        {"id": 1, "name": "Daniel"},
                        {"id": 2, "name": "Paul"},
                        {"id": 3, "name": "Eric"}
                    ],
                    "tasks": [
                        {"id": 1, "assignee": 1, "content": "task1", "manDay": 6},
                        {"id": 2, "assignee": 1, "content": "task2", "manDay": 1},
                        {"id": 3, "assignee": 2, "content": "task3", "manDay": 3},
                        {"id": 4, "assignee": 3, "content": "task4", "manDay": 5}
                    ]
                }
            ''')
    
            def expected = [
                    [taskId: 1, taskContent: 'task1', assignee: 'Daniel', manDay: 6],
                    [taskId: 4, taskContent: 'task4', assignee: 'Eric', manDay: 5],
                    [taskId: 3, taskContent: 'task3', assignee: 'Paul', manDay: 3]
            ]
    
            assert expected == GQ {
                from p in json.persons
                innerjoin t in json.tasks on t.assignee == p.id
                where t.id in [1, 3, 4]
                orderby t.manDay in desc
                select (taskId: t.id, taskContent: t.content, assignee: p.name, manDay: t.manDay)
            }.toList()
        """
    }

    @Test
    void "testGinq - ascii table - 1"() {
        assertScript '''
            def q = GQ {
                from n in [1, 2, 3]
                select n as first_col, n + 1 as second_col
            }
    
            def result = q.toString()
            println result
    
            def expected = '+-----------+------------+\\n| first_col | second_col |\\n+-----------+------------+\\n| 1         | 2          |\\n| 2         | 3          |\\n| 3         | 4          |\\n+-----------+------------+\\n\'
            assert expected == result
        '''
    }

    @Test
    void "testGinq - as List - 1"() {
        assertScript '''
            assert [4, 16, 36, 64, 100] == GQ {from n in 1..<11 where n % 2 == 0 select n ** 2} as List
        '''
    }

    @Test
    void "testGinq - as List - 2"() {
        assertScript '''
            @groovy.transform.CompileStatic
            def x() {
                GQ {from n in 1..<11 where n % 2 == 0 select n ** 2} as List
            }
            assert [4, 16, 36, 64, 100] == x()
        '''
    }

    @Test
    void "testGinq - customize GINQ target - 0"() {
        assertScript '''
            assert [0, 1, 2] == GQ('org.apache.groovy.ginq.provider.collection.GinqAstWalker') {
                from n in [0, 1, 2]
                select n
            }.toList()
        '''
    }
}
