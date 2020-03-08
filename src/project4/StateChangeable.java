
package project4;

import java.util.Enumeration;




public interface StateChangeable<E>  {
    
   abstract void changeState(E e);

	void changeState(Enumeration enumeration);
}
