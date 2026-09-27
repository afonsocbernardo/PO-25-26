package bci.work;

public enum Category {
   REFERENCE("Referência"),
   FICTION("Ficção"),
   SCITECH("Técnica e Científica");

   private String _name;

   Category(String name) {
      _name = name;
   }

   @Override
   public String toString() {
      return _name;
   }  

}

