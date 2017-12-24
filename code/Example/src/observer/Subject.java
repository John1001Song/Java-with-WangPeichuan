package observer;

/** Any "observable" subject will need to implement this interface. In this example, WeatherData
 * is a subject (observable). Example from Head First Design Patterns */
public interface Subject {
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers();
}
