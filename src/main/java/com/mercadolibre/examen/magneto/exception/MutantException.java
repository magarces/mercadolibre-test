package com.mercadolibre.examen.magneto.exception;

public class MutantException extends Exception {

  private static final long serialVersionUID = -605820262757431923L;

  public MutantException() {
  }

  public MutantException(String msg) {
    super(msg);
  }

  public MutantException(String msg, Throwable ex) {
    super(msg, ex);
  }

  public MutantException(Throwable ex) {
    super(ex);
  }

}
