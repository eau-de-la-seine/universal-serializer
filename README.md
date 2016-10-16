# universal-serializer
A serialization project for transforming Java objects to another format


## Serialization formats

Binary:
* Java

File:
* CSV
* Excel

Text:
* Base 64
* JWT
* XML


### Base64 and Base64 Url serialization

    AbstractBase64Serializer s = new Base64Serializer(); // OR new Base64UrlSerializer();
    String base64String = s.serialize(new MyClass(/* init */));
    MyClass newObject = s.unserialize(base64String);

### CSV serialization

    // TODO

### Excel serialization

    // TODO

### Java serialization  


    JavaSerializer s = new JavaSerializer();
    byte[] byteArray = s.serialize(new MyClass(/* init */));
    MyClass newObject = s.unserialize(byteArray);


### JWT (JSON Web Token) serialization  
  

    String SECRET = "546T78UINqqsvfzfs<vs<sdv_-('U87Y89YG87";
    JwtSerializer s = new JwtSerializer(Algorithm.HS256, MyClassClass.class, SECRET);
    String jsonWebToken = s.serialize(new MyClass(/* init */));
    MyClass newObject = s.unserialize(jsonWebToken);


### XML serialization


    XmlSerializer s = new XmlSerializer(MyClass.class);
    String xmlString = s.serialize(new MyClass(/* init */));
    MyClass newObject = s.unserialize(xmlString);

