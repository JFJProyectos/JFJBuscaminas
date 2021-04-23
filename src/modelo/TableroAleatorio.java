package modelo;

import java.util.ArrayList;

import utiles.Utiles;

public class TableroAleatorio extends Tablero {

	//Constructor aleatorio
	public TableroAleatorio(int lado, int minas) {
		super(lado);
		ArrayList<Coordenada> posiciones = generaAleatorio(minas, lado);
		disponerTablero(posiciones);
	}
	
	//constructor no aleatorio
	public TableroAleatorio(int lado,ArrayList<Coordenada> posiciones) {
		super(lado);
		disponerTablero(posiciones);
	}
	private void disponerTablero(ArrayList<Coordenada> posiciones) {
		colocarMinas(posiciones);
		contarMinasAlrededor(posiciones);
	}


	public void contarMinasAlrededor(ArrayList<Coordenada> posiciones) {

		for (Coordenada coordenada : posiciones) {
			for (int i = coordenada.getPosX()-1; i <= coordenada.getPosX()+1; i++) {
				for (int j = coordenada.getPosY()-1; j <= coordenada.getPosY()+1; j++) {
					if(i>=0 && j >= 0 && i< getAlto() && j< getAncho()) {
						Casilla casilla = getCasilla(new Coordenada(i, j));
						if(!casilla.isMina()) {
							casilla.setMinasAlrededor(casilla.getMinasAlrededor()+1);
						}
					}
				}
			}
		}
		
	}
	
	public boolean desvelarCasillas(Coordenada coord) {
		if(getCasilla(coord).isVelada()) {
			System.out.println(coord.getPosX()+":"+coord.getPosY()+", esta oculta");
			getCasilla(coord).setVelada(false);
			if(getCasilla(coord).isMina()) {
				System.out.println(coord.getPosX()+":"+coord.getPosY()+", es mina");
				return false;
			}else if(getCasilla(coord).getMinasAlrededor() > 0){
				System.out.println(coord.getPosX()+":"+coord.getPosY()+", es > 0");
				return false;
			}else {
				for (int i = coord.getPosX()-1; i <= coord.getPosX()+1; i++) {
					for (int j = coord.getPosY()-1; j <= coord.getPosY()-1; j++) {
						System.out.println(i+":"+j+", es 0");
						if(i>=0 && j >= 0 && i<= getAlto() && j <= getAncho()){
							System.out.println(i+":"+j+", es 0 y dentro limites");
							return desvelarCasillas(coord);
						}
					}
				}
			}
		}
		return false;
	}
	
	

	private void colocarMinas(ArrayList<Coordenada> posiciones) {
		for (Coordenada coordenada : posiciones) {
			ponerMina(coordenada);
		}
	}

	private void ponerMina(Coordenada coordenada) {
		getCasilla(coordenada).setMina(true);
	}

	public ArrayList<Coordenada> generaAleatorio(int minas, int longitud) {
		assert minas > 0 && longitud > 0;
		assert minas < longitud * longitud;
//		long inicio = System.currentTimeMillis();
		ArrayList<Coordenada> coordenadas = new ArrayList<Coordenada>();
		for (int i = 0; i < minas; i++) {
			Coordenada coord;
			do {
				coord = dameCoordenada(longitud);
			} while (existeCoord(coord, coordenadas));
			coordenadas.add(coord);
		}
//		 long fin = System.currentTimeMillis();
//		 System.out.println("en milis "+(fin-inicio));
		return coordenadas;
	}

	private Coordenada dameCoordenada(int lado) {
		return new Coordenada(Utiles.dameNumero(lado), Utiles.dameNumero(lado));
	}

	private boolean existeCoord(Coordenada coord, ArrayList<Coordenada> coordenadas) {
		for (int i = 0; i < coordenadas.size(); i++) {
			if (coord.equals(coordenadas.get(i))) {
				return true;
			}
		}
		return false;
	}
}
