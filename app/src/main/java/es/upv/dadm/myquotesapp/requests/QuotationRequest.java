package es.upv.dadm.myquotesapp.requests;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import es.upv.dadm.myquotesapp.pojo.Quotation;

public class QuotationRequest extends Request<Quotation> {

    private Response.Listener<Quotation> listener;
    private final String requestBody;

    public QuotationRequest(int method, String url, String body, Response.Listener<Quotation> listener,
                          Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
        this.requestBody = body;
    }

    @Override
    protected Response<Quotation> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Quotation newQuotation = new Gson().fromJson(json, Quotation.class);
            return Response.success(newQuotation, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void deliverResponse(Quotation quotationResponse) {
        this.listener.onResponse(quotationResponse);
    }

    @Override
    public byte[] getBody() {
        try {
            return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

}
