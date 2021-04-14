package ua.svasilina.targeton.utils.connection;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ua.svasilina.targeton.utils.storage.UserAccessStorage;

import static ua.svasilina.targeton.utils.constants.Keys.USER;

public class Connector {
    private static Connector connector;
    private RequestQueue requestQueue;
    String userToken;

    public static synchronized Connector getInstance() {
        if (connector == null){
            connector = new Connector();
        }
        return connector;
    }

    public <T> void addRequest(Context context, Request<T> req){
        final RequestQueue request = getRequest(context);
        request.add(req);
    }

    public RequestQueue getRequest(Context context){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    public void newJsonReq(Context context, String address, JSONObject jsonObject, Response.Listener<JSONObject> jsonObjectListener, Response.ErrorListener errorListener) {
        if (userToken == null){
            userToken = UserAccessStorage.getUserAccess(context);
        }
        System.out.println(jsonObject);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                address,
                jsonObject, jsonObjectListener,
                errorListener
                ){
            @Override
            public Map<String, String> getHeaders() {
                final HashMap<String, String> header = new HashMap<>();
                header.put(USER, userToken);
                return header;
            }
        };
        addRequest(context, request);
    }
}
