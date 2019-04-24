package com.vendas.cantinavirtual;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
//654654

import com.vendas.cantinavirtual.fragments.ClienteFragment;
import com.vendas.cantinavirtual.fragments.ProdutosFragment;
import com.vendas.cantinavirtual.fragments.VendasFragment;

import static com.vendas.cantinavirtual.R.id.fragment_container;



public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    addFragment(ClienteFragment.class);
                    return true;
                case R.id.navigation_dashboard:
                    addFragment(ProdutosFragment.class);
                    return true;
                case R.id.navigation_notifications:
                    addFragment(VendasFragment.class);
                    return true;
            }
            return false;
        }
    };

    private SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        try{

            bancoDados = openOrCreateDatabase("CantinaVirtual", MODE_PRIVATE, null);

            //TABELA CLIENTE
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS cliente(idCliente INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome varchar, telefone varchar, saldo real)");

            //TABELA PRODUTO
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS produto(idProduto INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome varchar, categoria varchar, preco real)");

            //TABELA VENDA
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS venda(idVenda INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "idCliente int, dataVenda date, FOREIGN KEY(idCliente) REFERENCES cliente(idCliente))");

            //TABELA VENDAPRODUTO
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS vendaProduto(idVendaProduto INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "idVenda int, idProduto int, quantidade int, valor real," +
                    "FOREIGN KEY(idVenda) REFERENCES venda(idVenda)," +
                    "FOREIGN KEY(idProduto) REFERENCES produto(idProduto))");

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addFragment(Class fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(fragment_container, fragment).commit();
    }
}
