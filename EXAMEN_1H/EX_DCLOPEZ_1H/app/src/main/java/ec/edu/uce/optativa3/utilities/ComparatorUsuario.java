package ec.edu.uce.optativa3.utilities;

import java.util.Comparator;

public class ComparatorUsuario {
    public static Comparator<Usuario> getCompByFechaNacimiento() {
        return new Comparator<Usuario>() {

            public int compare(Usuario o1, Usuario o2) {
                return o2.getFechaNacimiento().compareToIgnoreCase(o1.getFechaNacimiento());
            }
        };
    }

}
