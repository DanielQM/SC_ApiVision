package controller;

import dao.dao;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import model.model;
import org.json.JSONException;

@Named(value = "controller")
@SessionScoped
public class controller implements Serializable {
    
    model dl = new model();
    
    public void limpiar(){
    dl = new model();
    }

    public void apiVision() throws IOException, JSONException {
        dao dao;
        try {
            dao = new dao();
            dao.consultarapiVision(dl);
        } catch (IOException | JSONException e) {
            throw e;
        }
    }

    public model getDl() {
        return dl;
    }

    public void setDl(model dl) {
        this.dl = dl;
    }
 
}
