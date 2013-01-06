FEST-Reflect provides an intuitive, compact and type-safe fluent API for Java reflection. It makes Java reflection tremendously easy to use: no more casting, checked exceptions, PriviledgedActions or calls to setAccessible. FEST’s reflection module can even overcome the limitations of generics and type erasure.

Example:

```
Person person = constructor().withParameterTypes(String.class)
                             .in(Person.class)
                             .newInstance("Yoda");
 
method("setName").withParameterTypes(String.class)
                 .in(person)
                 .invoke("Luke");

