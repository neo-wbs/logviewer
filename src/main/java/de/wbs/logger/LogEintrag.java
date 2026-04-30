package de.wbs.logger;

//Wie class, aber Konstruktor, Eigenschaften und Getter (Name wie Eigenschaft) sind schon fertig
//Auch bestimmte Methoden sind überschrieben: toString, equals, hashcode
//Aber alles ist final: Klasse, Eigenschaften
public record LogEintrag(int zeilennummer, String level, String nachricht) { }
