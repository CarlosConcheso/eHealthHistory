package com.example.ehealthhistory.data.model;

import com.example.ehealthhistory.data.model.CareTeam.CareTeam;
import com.example.ehealthhistory.data.model.Club.Club;
import com.example.ehealthhistory.data.model.footballer.Footballer;
import com.example.ehealthhistory.data.model.footballer.FootballerComunication;
import com.example.ehealthhistory.data.model.footballer.FootballerContact;
import com.example.ehealthhistory.data.model.healthCareService.HealthCareAvalibleTime;
import com.example.ehealthhistory.data.model.healthCareService.HealthCareService;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ModelFactory {

    private FootballerComunication footballerComunication;
    private CareTeam careTeam;
    private Club club;
    private FootballerContact contact;
    private Footballer footballer;
    ArrayList<HealthCareService> healthcares = new ArrayList<HealthCareService>();
    private ArrayList<Footballer> footballers = new ArrayList<Footballer>();
    private ArrayList<CareTeam> careTeams = new ArrayList<CareTeam>();

    public ModelFactory()
    {
        inicializarDatos();
    }

    private void inicializarDatos()
    {
        footballerComunication = new FootballerComunication("Español", true);

        careTeam = new CareTeam(1, CareTeam.CareTeamStatus.active, "Equipo Medico 1",
            676889974, "Clínica Asturias");
        careTeams.add(careTeam);

        careTeam = new CareTeam(2, CareTeam.CareTeamStatus.inactive, "Rodas",
                676889974, "De lo mejor");
        careTeams.add(careTeam);

        careTeam = new CareTeam(3, CareTeam.CareTeamStatus.suspended, "Equipo Medico 3",
                676889974, "Clinica privada");
        careTeams.add(careTeam);

        careTeam = new CareTeam(4, CareTeam.CareTeamStatus.active, "Real Oviedo",
                676889974, "Los mejores medicos del club");
        careTeams.add(careTeam);

        club = new Club(1, true, "Real Oviedo", "Jorge Menéndez",
                "Equipo carbayón","Laura Manjoya", careTeam);


        contact = new FootballerContact("Borja",887662553,"Oviedo", club);

        footballer = new Footballer(1, true, "joanfemenias@realoviedo.es","Joan Femenias",
                615998876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(2, true, "tomeunadal@realoviedo.es","Tomeu Nadal",
                615998876, Footballer.genders.MALE, "15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(3, true, "carlosisaac@realoviedo.es","Carlos Isaac",
                615998876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(4, true, "lucasahijado@realoviedo.es","Lucas Ahijado",
                615998876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(5, true, "davidcostas@realoviedo.es","David Costas",
                615998876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(6, true, "danicalvo@realoviedo.es","Dani Calvo",
                615998876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(7, true, "rodritarin@realoviedo.es","Rodri Tarín",
                615998876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(8, true, "christianfernandez@realoviedo.es","Bolaño",
                615998876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(9, true, "mosaa@realoviedo.es","Mossa",
                615998876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(10, false, "pierrecornud@realoviedo.es","Pierre Cornud",
                615118876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(11, false, "jimmy@realoviedo.es","Jimmy Suarez",
                615118876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(12, false, "brugman@realoviedo.es","Gastón Brugman",
                615118876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(13, false, "luismi@realoviedo.es","Luismi",
                615118876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(14, false, "jonimontiel@realoviedo.es","Joni Montiel",
                615888876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(15, false, "hugorama@realoviedo.es","Hugo Rama",
                615888876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(16, true, "vitirozada@realoviedo.es","Viti Rozada",
                615118876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(17, true, "sangalli@realoviedo.es","Marco Sangalli",
                615118876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(18, true, "jirka@realoviedo.es","Erik Jirka",
                615118876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(19, true, "pombo@realoviedo.es","Jorge Pombo",
                615118876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(20, true, "borjasanchez@realoviedo.es","Borja Sánchez",
                615658876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(21, true, "samuelobeng@realoviedo.es","Samuel Obeng",
                611658876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(22, true, "matheus@realoviedo.es","Matheus Aias",
                611658876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        footballer = new Footballer(23, true, "borjabaston@realoviedo.es","Borja Bastón",
                611658876, Footballer.genders.MALE,"15/6/1997", contact, footballerComunication);
        footballers.add(footballer);

        establecerCuidados(footballer);

    }

    public void establecerCuidados(Footballer footballer)
    {

        // Establecemos cuidados del futbolista
        ArrayList<HealthCareAvalibleTime.daysOfWeek> diasTratamiento = new ArrayList<HealthCareAvalibleTime.daysOfWeek>();
        diasTratamiento.add(HealthCareAvalibleTime.daysOfWeek.MON);
        diasTratamiento.add(HealthCareAvalibleTime.daysOfWeek.THU);


        HealthCareService h1 = new HealthCareService(false, footballer, "Mantenimiento",
                "Fisioterapia", "Medicamentos: 1,1,2,2,3,4,22,8 y 100",
                new HealthCareAvalibleTime(diasTratamiento,true,"0","0"));
        HealthCareService h2 = new HealthCareService(false,footballer, "Recuperacion",
                "Rehabilitación", "Medicamentos: 1 y 4",
                new HealthCareAvalibleTime(diasTratamiento,false,"10:00","12:00"));
        HealthCareService h3 = new HealthCareService(false,footballer, "Estiramientos postpartido",
                "Estiramientos", "Medicamentos: 1,1,2,2,3,4 y 11",
                new HealthCareAvalibleTime(diasTratamiento,true,"0","0"));
        HealthCareService h4 = new HealthCareService(true, footballer, "Mantenimiento",
                "Reposo", "Medicamentos: 1,1,222,8 y 100",
                new HealthCareAvalibleTime(diasTratamiento,false,"20:00","21:00"));

        healthcares.add(h1);
        healthcares.add(h2);
        healthcares.add(h3);
        healthcares.add(h4);

        footballer.setHealthcares(healthcares);
    }

    public FootballerComunication getFootballerComunication()
    {
        return footballerComunication;
    }

    public CareTeam getCareTeamROV()
    {
        return careTeam;
    }

    public Club getClub()
    {
        return club;
    }

    public FootballerContact getFootballerContact()
    {
        return contact;
    }

    public Footballer getFootballer()
    {
        return footballer;
    }

    public ArrayList<Footballer> getFootballers()
    {
        return footballers;
    }

    public ArrayList<HealthCareService> getHealthcares() { return healthcares; }

    public ArrayList<CareTeam> getCareTeams() { return careTeams; }


}
