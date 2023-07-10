package ups.edu.ec.practicaseis.controlador;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;

/**
 *
 * @author estem
 */
public class ControladorFile {

    public ControladorFile() {
    }

    public boolean crearFileCarpeta(String rutaDirectorio) {
        try {
            File directorio = new File(rutaDirectorio);
            if (!directorio.exists()) {
                return directorio.mkdir();
            }
        } catch (NullPointerException nullPointerException) {
        }return false;
    }

    public boolean crearFileArchivo(String rutaDirectorio, String nombreArichivo) {
        File directorio = new File(rutaDirectorio);
        try {
            if (!directorio.exists()) {
                directorio.mkdir();
            }
            File archivoExtencion = new File(rutaDirectorio, nombreArichivo);
            return archivoExtencion.createNewFile();
        } catch (IOException ex) {
            return false;
        }
    }

    public boolean elminarFile(File fileElminar) {
        if (fileElminar.exists()) {
            if (fileElminar.isDirectory()) {
                File[] listaFile = fileElminar.listFiles();
                if (listaFile != null && listaFile.length > 0) {
                    for (File archivo : listaFile) {
                        this.elminarFile(archivo);
                    }
                }
                return fileElminar.delete();
            }
        }
        return false;
    }

    public void listar(JList<String> listaFile, File file) {
        DefaultListModel<String> modelo = new DefaultListModel<>();
        if (file.exists()) {
            for (File direcotorio : file.listFiles()) {
                modelo.addElement(direcotorio.getAbsolutePath());
            }
            listaFile.setModel(modelo);
        } else {
            listaFile.setModel(modelo);
        }
    }

    public void mostrar(JTextArea jTextArea, String file) {
        File fileDirectorio = new File(file);

        jTextArea.setText("Nombre: " + fileDirectorio.getName());
        jTextArea.append("\nAbsolute patch: " + fileDirectorio.getAbsolutePath());
        jTextArea.append("\nTamaño archivo: " + this.calcularTamañoArchivo(fileDirectorio.length()));
        jTextArea.append("\nUltima modificacion: " + this.formatoFecha(fileDirectorio.lastModified()));
        jTextArea.append("\nPermiso de lectura: " + fileDirectorio.canRead());
        jTextArea.append("\nPermiso de escritura: " + fileDirectorio.canWrite());
        jTextArea.append("\nPermiso de ejecución: " + fileDirectorio.canExecute());
    }

    private String calcularTamañoArchivo(long fileTamaño) {
        String[] unidades = {"B", "KB", "MB", "GB"};
        int index = 0;
        double tamano = fileTamaño;

        while (tamano >= 1024 && index < unidades.length - 1) {
            tamano /= 1024;
            index++;
        }
        DecimalFormat fileT = new DecimalFormat("#.##");
        return fileT.format(tamano) + " " + unidades[index];
    }

    private String formatoFecha(long fechaLong) {
        Date date = new Date(fechaLong);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

}
