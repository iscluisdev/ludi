package com.ludi.tt_ludi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

public class Gato extends AppCompatActivity implements View.OnClickListener{

    private int[][] tablero = new int[3][3];
    private boolean ganar;
    private final String[] consejos = { "La jarra del buen beber sugiere la cantidad de bebidas que podrías tomar en un día.",
                                        "Si te gusta el café, solo deberías tomar hasta 2 tazas en un día.",
                                        "Las bebidas que menos debes consumir son las que contienen azúcar.",
                                        "La leche es una fuente importante de calcio para tu organismo.",
                                        "El agua natural es muy importante porque nos ayuda a mantener la piel sana, es esencial para la digestión y previene el estreñimiento.",
                                        "¿Sabías que la jarra del buen beber consta de 6 niveles?",
                                        "Si tomas agua y te mandienes hidratado, tu organismo tendrá beneficios como activar órganos internos y bajar la presión sanguínea.",
                                        "El nivel más grande y más importante dentro de la jarra del buen beber es el agua.",
                                        "Trata de evitar el café y el té. Estas bebidas complican tu digestión.",
                                        "Consumir leche es sano mientras no la consumas en exceso."};
    private int pointer;
    ImageView casilla1, casilla2, casilla3, casilla4, casilla5, casilla6, casilla7, casilla8, casilla9;
    Button cuestionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gato);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button cuestionario = (Button) findViewById(R.id.btnCuestionario);

        casilla1 = (ImageView) findViewById(R.id.casilla1);
        casilla2 = (ImageView) findViewById(R.id.casilla2);
        casilla3 = (ImageView) findViewById(R.id.casilla3);
        casilla4 = (ImageView) findViewById(R.id.casilla4);
        casilla5 = (ImageView) findViewById(R.id.casilla5);
        casilla6 = (ImageView) findViewById(R.id.casilla6);
        casilla7 = (ImageView) findViewById(R.id.casilla7);
        casilla8 = (ImageView) findViewById(R.id.casilla8);
        casilla9 = (ImageView) findViewById(R.id.casilla9);

        casilla1.setOnClickListener(this);
        casilla2.setOnClickListener(this);
        casilla3.setOnClickListener(this);
        casilla4.setOnClickListener(this);
        casilla5.setOnClickListener(this);
        casilla6.setOnClickListener(this);
        casilla7.setOnClickListener(this);
        casilla8.setOnClickListener(this);
        casilla9.setOnClickListener(this);

        iniciar();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.casilla1:
                    if(tirar(0, 0, 1, casilla1))
                        generarTiro();
                break;
            case R.id.casilla2:
                    if(tirar(0, 1, 1, casilla2))
                        generarTiro();
                break;
            case R.id.casilla3:
                    if(tirar(0, 2, 1, casilla3))
                        generarTiro();
                break;
            case R.id.casilla4:
                    if(tirar(1, 0, 1, casilla4))
                        generarTiro();
                break;
            case R.id.casilla5:
                    if(tirar(1, 1, 1, casilla5))
                        generarTiro();
                break;
            case R.id.casilla6:
                    if(tirar(1, 2, 1, casilla6))
                        generarTiro();
                break;
            case R.id.casilla7:
                    if(tirar(2, 0, 1, casilla7))
                        generarTiro();
                break;
            case R.id.casilla8:
                    if(tirar(2, 1, 1, casilla8))
                        generarTiro();
                break;
            case R.id.casilla9:
                    if(tirar(2, 2, 1, casilla9))
                        generarTiro();
                break;
            case R.id.btnReiniciar:
                iniciar();
                break;
            case R.id.btnCuestionario:
                Intent intent = new Intent(this,CuestionarioAct5.class);
                startActivity(intent);
                break;
        }
    }

    //Tirar una ficha
    private boolean tirar (int i, int j, int jugador, ImageView casilla){
        if(i>2 || j>2 || i<0 || j<0 || !disponibilidad())
            return false;

            if(tablero[i][j]==0){
                tablero[i][j] = jugador;
                if(jugador == 1)
                    casilla.setImageResource(R.drawable.gatoX);
                else{
                    mostrarConsejo();
                    casilla.setImageResource(R.drawable.gatoO);
                }

                ganar = verificar(jugador);
                if(ganar){
                    cuestionario.setEnabled(true);
                    estadoTablero(false);
                    Toast toast = Toast.makeText(this, (jugador==1)?"¡Felicidades! Has ganado el juego :)":"¡Oops! Creo que la actividad ha ganado.",Toast.LENGTH_SHORT);
                    toast.show();
                }else if(!disponibilidad()){
                    cuestionario.setEnabled(true);
                    estadoTablero(false);
                    Toast toast = Toast.makeText(this, "¡Ya no hay más casillas!. El juego se ha terminado.",Toast.LENGTH_SHORT);
                    toast.show();
                }

                return true;
            }else{
                System.out.println("El jugador debe seleccionar otra casilla");
                return false;
            }

    }

    //Reiniciar valores
    private void iniciar(){
        cuestionario.setEnabled(false);
        pointer = 0;
        for(int i = 0 ; i<3; i++)
            for(int j = 0 ; j<3; j++)
                tablero[i][j]=0;
        ganar = false;
        casilla1.setImageResource(R.drawable.signo);
        casilla2.setImageResource(R.drawable.signo);
        casilla3.setImageResource(R.drawable.signo);
        casilla4.setImageResource(R.drawable.signo);
        casilla5.setImageResource(R.drawable.signo);
        casilla6.setImageResource(R.drawable.signo);
        casilla7.setImageResource(R.drawable.signo);
        casilla8.setImageResource(R.drawable.signo);
        casilla9.setImageResource(R.drawable.signo);
        estadoTablero(true);
    }

    //Revisar si alguien ya ganó
    private boolean verificar(int jugador){

        //Caso A: diagonal decreciente
        if( tablero[0][0] == jugador && tablero[0][0] == tablero[1][1] && tablero[1][1] == tablero[2][2] )
            return true;
        //Caso B: diagonal creciente
        if( tablero[2][0] == jugador && tablero[2][0] == tablero[1][1] && tablero[1][1] == tablero[0][2] )
            return true;
        //Caso C: línea horizontal
        if( tablero[1][0] == jugador && tablero[1][0] == tablero[1][1] && tablero[1][1] == tablero[1][2] )
            return true;
        //Caso D: línea vertical
        if( tablero[0][1] == jugador && tablero[0][1] == tablero[1][1] && tablero[1][1] == tablero[2][1] )
            return true;

        return false;

    }

    //Función matemática para obtener el mayor de dos enteros
    private int max(int a, int b){
        return a>b?a:b;
    }

    //Verificar si el tablero aún tiene campos vacíos
    private boolean disponibilidad(){
        for(int i = 0 ; i<3; i++)
            for(int j = 0 ; j<3; j++)
                if(tablero[i][j]==0)
                    return true;
        return false;
    }

    //Generar tiro de robot
    private void generarTiro(){

        int i,j;

        do{
            i = (int)(Math.random()*3);
            j = (int)(Math.random()*3);

        }while(!ganar && tablero[i][j]!=0);

        if(!ganar){
            tirar(i,j,2,obtenerImageView(i,j));
        }

    }

    //Obtener casilla segun posición del tablero
    private ImageView obtenerImageView(int i, int j){
        ImageView resultado = null;
        if(i==0 && j==0)
            resultado = casilla1;
        if(i==0 && j==1)
            resultado = casilla2;
        if(i==0 && j==2)
            resultado = casilla3;
        if(i==1 && j==0)
            resultado = casilla4;
        if(i==1 && j==1)
            resultado = casilla5;
        if(i==1 && j==2)
            resultado = casilla6;
        if(i==2 && j==0)
            resultado = casilla7;
        if(i==2 && j==1)
            resultado = casilla8;
        if(i==2 && j==2)
            resultado = casilla9;

        return resultado;

    }

    //Cada vez que la computadora tire, le mostratá información importante al niño.
    private void mostrarConsejo(){
        Toast toast = Toast.makeText(this,consejos[pointer],Toast.LENGTH_SHORT);
        toast.show();
        pointer = (pointer + 1) % 10;
    }

    //Estado tablero. Se habilita durante el juego, al terminarse el juego se deshabilita, puede ser ganando, perdiendo o solo se terminaron las casillas
    private void estadoTablero(boolean estado){
        casilla1.setEnabled(estado);
        casilla2.setEnabled(estado);
        casilla3.setEnabled(estado);
        casilla4.setEnabled(estado);
        casilla5.setEnabled(estado);
        casilla6.setEnabled(estado);
        casilla7.setEnabled(estado);
        casilla8.setEnabled(estado);
        casilla9.setEnabled(estado);
    }
    
}