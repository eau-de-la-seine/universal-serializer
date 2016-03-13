# universal-serializer
A serialization project for transforming Java objects to another format


  
### How to use universal-serializer

    class Dumb implements Serializable {
        int attr;
        
        public Dumb(){}
        
        public Dumb(int val){
            this.attr = val;
        }
        
        public int getAttr(){
            return attr;
        }
        
        public void setAttr(int val){
            attr = val;
        }
    }


### Java serialization  
  
    import fr.ekinci.universalserializer.*;
    import fr.ekinci.universalserializer.exception.*;
    
    JavaSerializer s = new JavaSerializer();
    byte[] byteArray = s.serialize(new Dumb(123456));
    Dumb newObject = s.unserialize(byteArray);
    System.out.println(newObject.getAttr());
  
### Json serialization  
  
    JsonSerializer s = new JsonSerializer(Dumb.class);
    String jsonString = s.serialize(new Dumb(123456));
    Dumb newObject = s.unserialize(jsonString);
    System.out.println(newObject.getAttr());

### XML serialization  
  
    XmlSerializer s = new XmlSerializer(Dumb.class);
    String xmlString = s.serialize(new Dumb(123456));
    Dumb newObject = s.unserialize(xmlString);
    System.out.println(newObject.getAttr());
    
### Base64 and Base64 Url serialization  
  
    AbstractBase64Serializer s = new Base64Serializer(); // OR new Base64UrlSerializer();
    String base64String = s.serialize(new Dumb(123456));
    Dumb newObject = s.unserialize(base64String);
    System.out.println(newObject.getAttr());

### JWT (JSON Web Token) serialization  
  
    String SECRET = "546T78UINqqsvfzfs<vs<sdvç_è-('éU87Y89YG87";
    JWTSerializer s = new JWTSerializer(Algorithm.HS256, DumbClass.class, SECRET);
    String jsonWebToken = s.serialize(new Dumb(123456));
    Dumb newObject = s.unserialize(jsonWebToken);
    System.out.println(newObject.getAttr());
