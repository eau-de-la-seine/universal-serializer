# universal-serializer
A serialization project for transforming Java objects to another format


## Serialization formats

Binary:
* Java
* Google Protocol Buffers

File:
* CSV
* Excel

Text:
* Base64 and Base64 Url
* JWT
* XML


## API

All implementation classes inherit from the `Serializer` interface, this common interface has the following methods :

* `serialize(X myObject) : Y` : For serializing your object to \[ binary | text | file \] data format
* `deserialize(Y mySerializedData) : X` : For deserializing your serialized data to Java object
* `sendTo(X myObject, OutputStream out) : void` : For serializing and sending your data through output stream (useful over network)
* `receiveFrom(InputStream in) : Y` : For deserializing object received through input stream (useful over network)



## File serialization

For file serialization you have to implement the `@FileInfo` annotation on your [DTO](https://en.wikipedia.org/wiki/Data_transfer_object) class.

This annotation exists for :

* `orderedFieldNames` (**mandatory**) : Selecting your Java fields (even parent fields).
* `headerColumnNames` (*optional*) : Giving names to your columns in the header (if you activate the header option)


You have builder pattern based `FileOptions` class in order to specify your :

* `dateFormat` : Date format ( 'yyyy-MM-dd' by default)
* `destinationPath` : Generated file's path (temp directory by default)
* `hasHeader` : Header in the file ( `false` by default)
* `sheetIndex` : Sheet number ( `0` by default)
* `excelFormat` : Excel format ( XLSX by default)
* `csvFormat` : CSV format separator ( RFC4180 by default)


## Implementations (alphabetical order)

### Base64 and Base64 Url serialization

    AbstractBase64Serializer<MyClass> s = new Base64Serializer<>(); // OR new Base64UrlSerializer<>();
    String base64String = s.serialize(new MyClass(/* init */));
    MyClass deserialized = s.deserialize(base64String);


### CSV serialization

    CSVSerializer<MyClass> s = new CSVSerializer<>(MyClass.class /* , your FileOptions */);
    Path path = s.serialize(new ArrayList<MyClass>());
    List<MyClass> deserialized = s.deserialize(path);


### Excel serialization

    ExcelSerializer<MyClass> s = new ExcelSerializer<>(MyClass.class /* , your FileOptions */);
    Path path = s.serialize(new ArrayList<MyClass>());
    List<MyClass> deserialized = s.deserialize(path);

### Google Protocol Buffers serialization

    ProtobufSerializer<MyClass> s = new ProtobufSerializer<>(MyClass.class);
    byte[] byteArray = s.serialize(MyClass.newBuilder().build());
    MyClass deserialized = s.deserialize(byteArray);

### Java serialization  

    JavaSerializer<MyClass> s = new JavaSerializer<>();
    byte[] byteArray = s.serialize(new MyClass(/* init */));
    MyClass deserialized = s.deserialize(byteArray);


### JWT (JSON Web Token) serialization  

    String SECRET = "546T78UINqqsvfzfs<vs<sdv_-('U87Y89YG87";
    JwtSerializer<MyClass> s = new JwtSerializer<>(Algorithm.HS256, MyClassClass.class, SECRET);
    String jsonWebToken = s.serialize(new MyClass(/* init */));
    MyClass deserialized = s.deserialize(jsonWebToken);


### XML serialization

    XmlSerializer<MyClass> s = new XmlSerializer<>(MyClass.class);
    String xmlString = s.serialize(new MyClass(/* init */));
    MyClass deserialized = s.deserialize(xmlString);

