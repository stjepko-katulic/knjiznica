package hr.yossarian.knjiznica.modeli;

public class Counter {

  private static int counter = 0;

  public static int incrementAndGet() {
    return ++counter;
  }

  public static void resetCounter() {
    counter=0;
  }


}
