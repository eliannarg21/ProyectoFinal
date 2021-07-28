package Recursos;

import java.util.ArrayList;

public abstract class Persona {
	
	protected String id;
	protected boolean status;
	protected String nombre;
	protected int edad;
	protected String sexo;
	protected String telefono;
	protected String direccion;
	protected String provincia;
	protected ArrayList<SolicitudPersona> misSolicitudes;

public Persona(String id, String nombre, int edad, String sexo, String telefono, String direccion, String provincia) {
	super();
	this.id = id;
	this.status = false;
	this.nombre = nombre;
	this.edad = edad;
	this.sexo = sexo;
	this.telefono = telefono;
	this.direccion = direccion;
	this.provincia = provincia;
	this.misSolicitudes = new ArrayList<>();
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public boolean isStatus() {
	return status;
}

public void setStatus(boolean status) {
	this.status = status;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public int getEdad() {
	return edad;
}

public void setEdad(int edad) {
	this.edad = edad;
}

public String getSexo() {
	return sexo;
}

public void setSexo(String sexo) {
	this.sexo = sexo;
}

public String getTelefono() {
	return telefono;
}

public void setTelefono(String telefono) {
	this.telefono = telefono;
}

public String getDireccion() {
	return direccion;
}

public void setDireccion(String direccion) {
	this.direccion = direccion;
}

public String getProvincia() {
	return provincia;
}

public void setProvincia(String provincia) {
	this.provincia = provincia;
}

public ArrayList<SolicitudPersona> getMisSolicitudes() {
	return misSolicitudes;
}

public void setMisSolicitudes(ArrayList<SolicitudPersona> misSolicitudes) {
	this.misSolicitudes = misSolicitudes;
}

}


