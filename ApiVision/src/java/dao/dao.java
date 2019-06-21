package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import model.model;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class dao {

    public model consultarApiVision(model mode) throws JSONException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost request = new HttpPost("https://vision.googleapis.com/v1/images:annotate?key=AIzaSyBX3BL1JNBGecWxxkF5OZrFeJA8TVbTZsA");// creamos la conexion con el API
            StringEntity params = new StringEntity("{\n"
                    + "  \"requests\": [\n"
                    + "    {\n"
                    + "      \"image\": {\n"
                    + "        \"source\": {\n"
                    + "          \"gcsImageUri\": \"gs://danielqm/" + mode.getNombreArchivo() + "\"\n"
                    + "        }\n"
                    + "      },\n"
                    + "      \"features\": [\n"
                    + "        {\n"
                    + "          \"type\": \"TEXT_DETECTION\"\n"
                    + "        }\n"
                    + "      ]\n"
                    + "    }\n"
                    + "  ]\n"
                    + "}");

            request.addHeader("Content-Type", "application/json"); // agrega un nuevo encabezado HTML que le decimos sera un tipo : aplicacion json
            request.addHeader("Authorization", "Bearer " + mode.getToken());// agrega un nuevo encabezado HTML el cual sera de Autorizacion : Bearer Token y añade el Token
            request.setEntity(params); // Obtiene la solicitud de recursos
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            JSONObject json = new JSONObject(EntityUtils.toString(entity));
            mode.setRespuesta(json.getJSONArray("responses").getJSONObject(0).getJSONObject("fullTextAnnotation").getString("text"));
        } catch (IOException ex) {
            throw ex;
        }
        return mode;
    }

    public void envioStorage(model mode) {
        try {
            //Leo fichero
            String filename = mode.getRutaImagen();

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String lineaTotal = "";
            String linea = reader.readLine();
            while (linea != null) {
                lineaTotal = lineaTotal + linea;
                linea = reader.readLine();
            }

            String envio = lineaTotal;
            System.out.println("TAMAÑO KILOBYTES: " + (lineaTotal.length() / 1024));
            reader.close();
            StringEntity params = new StringEntity(envio);

            HttpClient httpClient = new DefaultHttpClient();
            model dl = new model();

            HttpPost request = new HttpPost("https://www.googleapis.com/upload/storage/v1/b/danielqm/o?uploadType=media&name=" + mode.getImagen());
            request.addHeader("Content-Type", "image/jpeg");
            request.addHeader("Authorization", "Bearer " + mode.getToken());
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
