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

public class ListRefrigeradorAdapter extends BaseAdapter {

    private List<RefrigeradorCtrl> refrigeradores;
    private Context context;

    public ListRefrigeradorAdapter(){}
    public ListRefrigeradorAdapter(Context context, List<RefrigeradorCtrl> refrigeradores) {
        this.refrigeradores = refrigeradores;
        this.context = context;
    }
    @Override
    public int getCount() {
        return refrigeradores.size();
    }

    @Override
    public Object getItem(int position) {
        return refrigeradores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return refrigeradores.get(position).getId_refri();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootView = LayoutInflater.from(context).inflate(R.layout.list_refrigerador_adapter, parent, false);

        TextView textMarca = (TextView) rootView.findViewById(R.id.textMarca);
        TextView textLotacionamento = (TextView) rootView.findViewById(R.id.textLotacionamento);
        TextView textDescri = (TextView) rootView.findViewById(R.id.textDescri);

//ajeitar o listview para listar os itens do banco na sequencia certa
        SQLiteHandler db = new SQLiteHandler(context);
        RefrigeradorCtrl refrigeradorDaVez = refrigeradores.get(position);

        Log.e("TEste --- Adpt ","tamanho refrigeradores: "+refrigeradores.size());
        Log.e("TEste --- Adpt ","refrigerador: "+refrigeradores.get(position).toString());
        Log.e("TEste --- Adpt ","refrigerador: "+refrigeradores.get(position).getMarca());
        if(refrigeradorDaVez.getId_refri() != 0) {
            textMarca.setText(db.getNomeMaca(refrigeradorDaVez.getMarca()));
            textLotacionamento.setText(refrigeradorDaVez.getLotacionamento());
            textDescri.setText(db.getNomeModelo(refrigeradorDaVez.getTipo_modelo()) + " - "
                    + db.getNomeBTU(refrigeradorDaVez.getCapaci_termica()) + "BTUs, "
                    + refrigeradorDaVez.getNivel_econo() + " - "
                    + refrigeradorDaVez.getPeso() + "Kg");
        }else {
            textMarca.setText("");
            textLotacionamento.setText("Sem Refrigerador...");
            textDescri.setText("");
        }
        return rootView;
    }
}
