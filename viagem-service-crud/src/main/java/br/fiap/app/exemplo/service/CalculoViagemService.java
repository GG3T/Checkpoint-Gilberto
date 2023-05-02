package br.fiap.app.exemplo.service;


import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CalculoViagemService {


    public String calculate(int estadia, String decolagem) throws ParseException {

        final int tempoTotal = 2 * 260 + estadia;
        final String timestampRetorno = (decolagem + tempoTotal * 24 * 60 * 60 * 1000L);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(timestampRetorno);

        return date.toString();
    }

}
