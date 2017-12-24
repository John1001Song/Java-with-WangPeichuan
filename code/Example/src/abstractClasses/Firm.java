package abstractClasses;
//********************************************************************
//  Firm.java       Author: Lewis/Loftus
//
//  Demonstrates the use of an abstract class, polymorphism via inheritance.
//********************************************************************

public class Firm
{
   //-----------------------------------------------------------------
   //  Creates a staff of employees for a firm and pays them.
   //-----------------------------------------------------------------
   public static void main(String[] args)
   {
      Staff personnel = new Staff();

      personnel.payday();
   }
}
