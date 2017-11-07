package com.example.douglas.dd_os_cliente.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.douglas.dd_os_cliente.R;
import com.example.douglas.dd_os_cliente.controler.RefrigeradorCtrl;
import com.example.douglas.dd_os_cliente.controler.ServPendenteCtrl;
import com.example.douglas.dd_os_cliente.helper.SQLiteHandler;

import java.util.List;

public class ListServPenCliAdapter extends BaseAdapter {

    private List<ServPendenteCtrl> serPens;
    private Context context;

    public ListServPenCliAdapter(){}
    public ListServPenCliAdapter(Context context, List<ServPendenteCtrl> serPens) {
        this.serPens = serPens;
        this.context = context;
    }
    @Override
    public int getCount() {
        return serPens.size();
    }

    @Override
    public Object getItem(int position) {
        return serPens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return serPens.get(position).getId_serv_pen();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootView = LayoutInflater.from(context).inflate(R.layout.activity_list_serv_pen_adapter, parent, false);

        TextView tvRefriLota = (TextView) rootView.findViewById(R.id.nomeRefriLota);
        TextView tvHoraServ = (TextView) rootView.findViewById(R.id.horaServ);
        TextView tvDataServ = (TextView) rootView.findViewById(R.id.dataServ);
        TextView tvStatusServ = (TextView) rootView.findViewById(R.id.statusServ);

//ajeitar o listview para listar os itens do banco na sequencia certa
        SQLiteHandler db = new SQLiteHandler(context);
        ServPendenteCtrl msgDaVez = serPens.get(position);
        RefrigeradorCtrl refri = db.getArCli(msgDaVez.getId_refriCli());

        Log.e("TEste --- Adpt ","tamanho Service: "+serPens.size());
        Log.e("TEste --- Adpt ","Service: "+serPens.get(position).toString());
        Log.e("TEste --- Adpt ","serv ID: "+serPens.get(position).getId_serv_pen());
        if(msgDaVez.getId_serv_pen() != 0) {
            tvRefriLota.setText(db.getNomeMaca(refri.getMarca()) + " - " + refri.getLotacionamento());
            tvHoraServ.setText(msgDaVez.getHora_serv().toString());
            tvDataServ.setText(msgDaVez.getData_serv().toString());

            tvStatusServ.setText(msgDaVez.getStatus_serv());

        }else{
            tvRefriLota.setText("Sem Serviço Solicitado!!!");
            tvHoraServ.setText(" ");
            tvDataServ.setText("");
            tvStatusServ.setText("Solicite um Serviço!!!");
        }
        return rootView;
    }
}
