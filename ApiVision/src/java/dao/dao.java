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

    public model consultarapiVision(model dl) throws JSONException, IOException {
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpPost request = new HttpPost("https://vision.googleapis.com/v1/images:annotate?key=AIzaSyD2LhHdvtTLXH47LQS_tsPiLrIxi-aRdwA");// creamos la conexion con el API
            StringEntity params = new StringEntity("{\n"
                    + "  \"requests\": [\n"
                    + "    {\n"
                    + "      \"image\": {\n"
                    + "        \"source\": {\n"
                    + "          \"gcsImageUri\": \"gs://danielqm/" + dl.getNameFile() + "\"\n"
                    + "        }\n"
                    + "      },\n"
                    + "      \"features\": [\n"
                    + "        {\n"
                    + "          \"type\":\"DOCUMENT_TEXT_DETECTION\",\n" //Tipo de Api a Utilizar
                    + "          \"maxResults\":1\n" 
                    + "        }\n"
                    + "      ]\n"
                    + "    }\n"
                    + "  ]\n"
                    + "}");

            request.addHeader("Content-Type", "application/json"); // agrega un nuevo encabezado HTML que le decimos sera un tipo : aplicacion json
            request.addHeader("Authorization", "Bearer " + dl.getToken_acces());// agrega un nuevo encabezado HTML el cual sera de Autorizacion : Bearer Token y añade el Token
            request.setEntity(params); // Obtiene la solicitud de recursos
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            JSONObject json = new JSONObject(EntityUtils.toString(entity));
            dl.setResponses(json.getJSONArray("responses").getJSONObject(0).getJSONObject("fullTextAnnotation").getString("text"));
        } catch (IOException ex) {
            throw ex;
        }
        return dl;
    }
}
