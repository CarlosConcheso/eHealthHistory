package com.example.ehealthhistory.data.Model.CareTeam;

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

    private int id;
    private String username;
    private String CIF;
    private String status;
    private String name;
    private int telcom;
    private String note;

    public CareTeam()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCIF() {
        return CIF;
    }

    public void setCIF(String CIF) {
        this.CIF = CIF;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getTelecom() {
        return telcom;
    }

    public void setTelcom(int telcom) {
        this.telcom = telcom;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
