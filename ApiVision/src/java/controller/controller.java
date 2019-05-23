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

    model mode = new model();

    public void limpiar() {
        mode = new model();
    }

    public void consultarApiVision() throws IOException, JSONException {
        dao dao;
        try {
            dao = new dao();
            dao.consultarApiVision(mode);
        } catch (IOException | JSONException e) {
            throw e;
        }
    }

    public model getMode() {
        return mode;
    }

    public void setMode(model mode) {
        this.mode = mode;
    }
}
