package Recursos;

import java.io.Serializable;
import java.util.ArrayList;

public class Control implements Serializable{
	
	private static final long serialVersionUID = -2707581395699323652L;
	private ArrayList<Usuario> usuarios;
	private ArrayList<Empresa> empresas;
	private ArrayList<Persona> personas;
	private ArrayList<SolicitudEmpresa> soliEmpresas;
	private ArrayList<SolicitudPersona> soliPersonas;
	private static Control control = null;
	private static Usuario loginUser;

	public Control() {
		usuarios = new ArrayList<>();
		empresas = new ArrayList<>();
		personas = new ArrayList<>();
		soliEmpresas = new ArrayList<>();
		soliPersonas = new ArrayList<>();
		Usuario admin = new Usuario("Administrador", "admin", "admin");
		usuarios.add(admin);
	}
	
	public static Control getInstance() {
		if (control == null) {
			control = new Control();
		}
		return control;
	}

	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public ArrayList<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(ArrayList<Empresa> empresas) {
		this.empresas = empresas;
	}

	public ArrayList<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(ArrayList<Persona> personas) {
		this.personas = personas;
	}

	public ArrayList<SolicitudEmpresa> getSoliEmpresas() {
		return soliEmpresas;
	}

	public void setSoliEmpresas(ArrayList<SolicitudEmpresa> soliEmpresas) {
		this.soliEmpresas = soliEmpresas;
	}

	public ArrayList<SolicitudPersona> getSoliPersonas() {
		return soliPersonas;
	}

	public void setSoliPersonas(ArrayList<SolicitudPersona> soliPersonas) {
		this.soliPersonas = soliPersonas;
	}
	
	public static Usuario getLoginUser() {
		return loginUser;
	}

	public static void setLoginUser(Usuario loginUser) {
		Control.loginUser = loginUser;
	}

	public static Control getControl() {
		return control;
	}

	public static void setControl(Control control) {
		Control.control = control;
	}

	//Metodo Buscar empresa por ID
	public Empresa BuscarEmpresa(String idEmpresa) {
		for(Empresa e: empresas) {
			if(e.getIdEmpresa().equalsIgnoreCase(idEmpresa)){
				return e;
			}
		}
		return null;
	}
	
	//Metodo registrar nueva empresa
	public void RegistrarEmpresa(Empresa nuevaEmpresa) {
		empresas.add(nuevaEmpresa);
	}
	 
	//Registrar una nueva persona
	public void RegistrarPersona(Persona nuevaPersona) {
		personas.add(nuevaPersona);
	}
	
	//Metodo para buscar persona por su ID
	public Persona BuscarPersona(String id) {
		for(Persona p: personas) {
			if(p.getId().equalsIgnoreCase(id)) {
				return p;
			}
		}
		return null;
	}
	
	public SolicitudPersona BuscarSoliPersona(String id) {
		for(SolicitudPersona sp: soliPersonas) {
			if(sp.getId().equalsIgnoreCase(id)) {
				return sp;
			}
		}
		return null;
	}
	
	public SolicitudEmpresa BuscarSoliEmpresa(String id) {
		for(SolicitudEmpresa se: soliEmpresas) {
			if(se.getIdSolicitud().equalsIgnoreCase(id)) {
				return se;
			}
		}
		return null;
	}
	
	//Metodo para generar solicitud de persona
	public void generarSolicitudPersona(String idPerson, SolicitudPersona nuevaSolicitud) {
		Persona p = BuscarPersona(idPerson);
		p.getMisSolicitudes().add(nuevaSolicitud);
		control.soliPersonas.add(nuevaSolicitud);
	}
	
	//Metodo para generar solicitud de empresa
	public void generarSolicitudEmpresa(String idEmpresa, SolicitudEmpresa newSolicitud) {
		Empresa e = BuscarEmpresa(idEmpresa);
		e.getSolicitudes().add(newSolicitud);
		control.soliEmpresas.add(newSolicitud);
	}

	//Metodo para realizar el match de solicitudes
	public SolicitudPersona match(SolicitudEmpresa solicitud) {
		SolicitudPersona person = null;
		int mayor = 0;
		
		for (SolicitudPersona soliPer : soliPersonas) {
			if (!soliPer.getPerson().isStatus() && solicitud.isEstado() && checkSimilitud(soliPer, solicitud)) {
				if (soliPer.getEstado().equalsIgnoreCase("Activa") && porcentaje(soliPer, solicitud) >= 70 && porcentaje(soliPer, solicitud) > mayor) {
					person = soliPer;
					mayor = porcentaje(soliPer, solicitud);
				}
			}
		}
		return person;
	}

	public int porcentaje(SolicitudPersona soliPer, SolicitudEmpresa soliEmp) {
		int porciento = 0;
		
		if (soliPer.getExperiencia() >= soliEmp.getExperienciaSolicitud())
			porciento++;
		if (soliPer.getLicenciaConducir().equalsIgnoreCase(soliEmp.getLincenciaConducirSolicitud()))
			porciento++;
		if (soliPer.getSueldoMinimo() <= soliEmp.getSueldoMaximoSolicitud())
			porciento++;
		if (soliPer.getTipoTrabajo().equalsIgnoreCase(soliEmp.getTipoTrabajoSolicitud()))
			porciento++;
		if (soliPer.getPerson().getEdad() >= soliEmp.getEdadMinima())
			porciento++;
		
		if (soliPer.getPerson().getDireccion().equalsIgnoreCase(soliEmp.getMiEmpresa().getDireccionEmpresa()))
			porciento++;
		else if (soliPer.getMovilidad().equalsIgnoreCase("Si"))
			porciento++;
		
		for (String idioma : soliEmp.getIdiomasSolicitud()) {
			for (String idio : soliPer.getIdiomas()) {
				if (idioma.equalsIgnoreCase(idio)) {
					porciento++;
				}
			}
		}
		
		float size = 6 + soliEmp.getIdiomasSolicitud().size();
		return (int) ((porciento/size)*100);
	}
	
	public boolean checkSimilitud(SolicitudPersona soliPerson, SolicitudEmpresa soliEmpresa) {
		boolean check = false;
		
		if (soliPerson.getPerson() instanceof Tecnico) {
			if (soliEmpresa.getTipoEmpleado().equalsIgnoreCase("Tecnico")) {
				Tecnico t = (Tecnico) soliPerson.getPerson();
				if (soliEmpresa.getTituloEmpleado().equalsIgnoreCase(t.getArea())) {
					check = true;
				}
			}
		} else if (soliPerson.getPerson() instanceof Universitario) {
			if (soliEmpresa.getTipoEmpleado().equalsIgnoreCase("Universitario")) {
				Universitario u = (Universitario) soliPerson.getPerson();
				if (soliEmpresa.getTituloEmpleado().equalsIgnoreCase(u.getTitulo())) {
					check = true;
				}
			}
		}
		
		return check;
	}
	
	public void changeToDesempleado(Persona person) {
		if (person.isStatus()) {
			person.setStatus(false);
			for (SolicitudPersona solicitud : person.getMisSolicitudes()) {
				if (solicitud.getEstado().equalsIgnoreCase("En espera")) {
					solicitud.setEstado("Activa");
				}
			}
		}
	}
	
	public void changeToEmpleado(Persona person) {
		if (!person.isStatus()) {
			person.setStatus(true);
			for (SolicitudPersona solicitud : person.getMisSolicitudes()) {
				if (solicitud.getEstado().equalsIgnoreCase("Activa")) {
					solicitud.setEstado("En espera");
				}
			}
		}
	}
	
	public boolean confirmLogin(String username, String password) {
		boolean login = false;
		for (Usuario user : usuarios) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				loginUser = user;
				login = true;
			}
		}
		return login;
	}
	
	public void completarSolicitud(SolicitudEmpresa se) {
		if (se.getCantidad() > 1) {
			se.setCantidad(se.getCantidad()-1);
		} else {
			se.setEstado(false);
		}
	}
	
	public void regUser(Usuario user) {
		usuarios.add(user);
	}
	
	public Usuario findUsuarioByUsername(String username) {
		for (Usuario user : usuarios) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
}
