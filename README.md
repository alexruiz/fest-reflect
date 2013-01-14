FEST-Reflect provides an intuitive, compact and type-safe fluent API that makes [Java reflection](http://docs.oracle.com/javase/tutorial/reflect/index.html) tremendously easy to use: no more casting, checked exceptions, ```PriviledgedAction```s or calls to ```setAccessible```. FEST’s reflection module can even overcome the limitations of [generics](http://docs.oracle.com/javase/tutorial/java/generics/) and [type erasure](http://docs.oracle.com/javase/tutorial/java/generics/erasure.html).

Example:

```
// import static org.fest.reflect.core.Reflection.*;

// Loads the class 'org.republic.Jedi'
Class<?> jediType = type("org.republic.Jedi").load();

// Loads the class 'org.republic.Jedi' as 'org.republic.Person' (Jedi extends Person)
Class<Person> jediType = type("org.republic.Jedi").loadAs(Person.class);

// Loads the class 'org.republic.Jedi' using a custom class loader
Class<?> jediType = type("org.republic.Jedi").withClassLoader(myClassLoader).load();

// Gets the inner class 'Master' in the declaring class 'Jedi':
Class<?> masterClass = innerClass("Master").in(Jedi.class).get();

// Equivalent to invoking 'new Person()'
Person p = constructor().in(Person.class).newInstance();

// Equivalent to invoking 'new Person("Yoda")'
Person p = constructor().withParameterTypes(String.class).in(Person.class).newInstance("Yoda");

// Retrieves the value of the field "name"
String name = field("name").ofType(String.class).in(person).get();

// Sets the value of the field "name" to "Yoda"
field("name").ofType(String.class).in(person).set("Yoda");

// Retrieves the value of the field "powers"
List<String> powers = field("powers").ofType(new TypeRef<List<String>>() {}).in(jedi).get();

// Equivalent to invoking the method 'person.setName("Luke")'
method("setName").withParameterTypes(String.class)
                 .in(person)
                 .invoke("Luke");

// Equivalent to invoking the method 'jedi.getPowers()'
List<String> powers = method("getPowers").withReturnType(new TypeRef<List<String>>() {})
                                         .in(person)
                                         .invoke();

// Retrieves the value of the static field "count" in Person.class
int count = field("count").ofType(int.class).in(Person.class).get();

// Sets the value of the static field "count" to 3 in Person.class
field("count").ofType(int.class).in(Person.class).set(3);

// Retrieves the value of the static field "commonPowers" in Jedi.class
List<String> commmonPowers = field("commonPowers").ofType(new TypeRef<List<String>>() {}).in(Jedi.class).get();

// Equivalent to invoking the method 'person.concentrate()'
method("concentrate").in(person).invoke();

// Equivalent to invoking the method 'person.getName()'
String name = method("getName").withReturnType(String.class)
                               .in(person)
                               .invoke();

// Equivalent to invoking the static method 'Jedi.setCommonPower("Jump")'
method("setCommonPower").withParameterTypes(String.class)
                        .in(Jedi.class)
                        .invoke("Jump");

// Equivalent to invoking the static method 'Jedi.addPadawan()'
method("addPadawan").in(Jedi.class).invoke();

// Equivalent to invoking the static method 'Jedi.commonPowerCount()'
String name = method("commonPowerCount").withReturnType(String.class)
                                        .in(Jedi.class)
                                        .invoke();

// Equivalent to invoking the static method 'Jedi.getCommonPowers()'
List<String> powers = method("getCommonPowers").withReturnType(new TypeRef<List<String>>() {})
                                               .in(Jedi.class)
                                               .invoke();

// Retrieves the value of the property "name"
String name = property("name").ofType(String.class).in(person).get();

// Sets the value of the property "name" to "Yoda"
property("name").ofType(String.class).in(person).set("Yoda");

