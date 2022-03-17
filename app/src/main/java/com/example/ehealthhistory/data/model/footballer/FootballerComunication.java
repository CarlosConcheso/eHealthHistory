package com.example.ehealthhistory.data.model.footballer;

/**
 * Comunicación con el futbolista. Incluye el lenguaje con el que se debería hablar con él
 * y si es preferible.
 */
public class FootballerComunication {
    private String lenguage;
    private boolean prefered;

    public FootballerComunication(String lenguaje, boolean prefered)
    {
        this.lenguage=lenguaje;
        this.prefered=prefered;
    }

    public FootballerComunication()
    {
    }

    public String getLenguage() {
        return lenguage;
    }

    public void setLenguage(String lenguage) {
        this.lenguage = lenguage;
    }

    public boolean isPrefered() {
        return prefered;
    }

    public void setPrefered(boolean prefered) {
        this.prefered = prefered;
    }
}
