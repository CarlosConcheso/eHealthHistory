package com.example.ehealthhistory.data.model.CareTeam;

import com.example.ehealthhistory.data.model.footballer.Footballer;
import org.intellij.lang.annotations.Identifier;

import java.util.ArrayList;

/**
 * o	Alcance y Uso:
 *      	personas y organizaciones que planean participar en la coordinación y la prestación de atención a un paciente
 *      	Todas las personas, equipos y organizaciones que planean participar en la coordinación y prestación
 *          de atención para un solo paciente o un grupo.
 *      	Se puede asignar organizativamente sin un tema en contexto,
 *          como un equipo de código azul o un equipo de respuesta de emergencia.
 *      	Abarca: médicos, familiares, tutores, el propio paciente u otros.
 *      	El equipo de cuidados de una persona puede ser dinámico a lo largo del tiempo,
 *          de modo que los miembros del equipo pueden ser transitorios, como un equipo de rehabilitación.
 */
public class CareTeam {

    //public enum CareTeamStatus { active , suspended, inactive, entered_in_error}

    private int id;
    private String status;
    private String name;
    private int telcom;
    private String note;

    public CareTeam()
    {

    }

    public CareTeam(int id, String status, String name,
                    int telcom, String note) {
        this.id=id;
        this.status = status;
        this.name = name;
        this.telcom = telcom;
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTelcom() {
        return telcom;
    }

    public void setTelecom(int telcom) {
        this.telcom = telcom;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
