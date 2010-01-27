package acabativa.grafico.action;

import java.awt.Point;

public class LineWalker {
	
	Double pass;
	Double error;
	
	public LineWalker(Double pass, Double error) {
		super();
		this.pass = pass;
		this.error = error;
	}

	public LineWalker(Double pass) {
		super();
		this.pass = pass;
	}
	
	public LineWalker() {
		super();
		this.pass = 1d;
	}
	
	public boolean hasIteration(Point start, Point end, int iteration){
		Point p = getPoint(start, end, iteration);
		if(p==null){
			return false;
		}
		else{
			return true;
		}
	}
	
	public Point getPoint(Point start, Point end, double bezierCoeficient) throws IllegalArgumentException{
		if(start==null || end==null){
			throw new IllegalArgumentException("Point cannot be null");
		}
		
		if(bezierCoeficient<0 || bezierCoeficient>1){
			throw new IllegalArgumentException("Bad Bezier coeficient");
		}
		
		Double hipotenusa = getHipotenusa(start, end);
		
		Point point = new Point();
		
		Double xCoef = getXCoeficient(start, end);
		Double yCoef = getYCoeficient(start, end);
				
		Double xValue = (xCoef * bezierCoeficient * hipotenusa) + start.getX();
		Double yValue = (yCoef * bezierCoeficient * hipotenusa) + start.getY();
		
		point.setLocation(xValue, yValue);
		
		return point;
	}
	
	public Point getPoint(Point start, Point end, int iteration) throws IllegalArgumentException{
		if(start==null || end==null){
			throw new IllegalArgumentException("Point cannot be null");
		}
		
		Point point = new Point();
		Double xCoef = getXCoeficient(start, end);
		Double yCoef = getYCoeficient(start, end);
		Double xValue = getNextValueX(start, xCoef, iteration);
		Double yValue = getNextValueY(start, yCoef, iteration);
				
		if(xCoef>0 && xValue>end.getX()){
			return null;
		}
		
		if(xCoef<0 && xValue<end.getX()){
			return null;
		}
		
		if(yCoef>0 && yValue>end.getY()){
			return null;
		}
		
		if(yCoef<0 && yValue<end.getY()){
			return null;
		}
		
		point.setLocation(xValue, yValue);
		
		return point;		
	}
			
	private double getNextValueX(Point start, double angularCoeficient, int iteration){
		double inc = pass*angularCoeficient*iteration;
		return start.getX() + inc;
	}
	
	private double getNextValueY(Point start, double invAngularCoeficient, int iteration){
		double inc = pass*invAngularCoeficient*iteration;
		return start.getY() + inc;
	}
	
	private Double getXCoeficient(Point start, Point end){
								
		double catetoAdjacente = 0; 
		
		catetoAdjacente = -start.getX()+end.getX();
				
		double hipotenusa = getHipotenusa(start, end);
								
		return catetoAdjacente/hipotenusa;
	}
	
	private Double getYCoeficient(Point start, Point end){
				
		double catetoOposto = 0;
		
		catetoOposto = -start.getY()+end.getY();
		
		double hipotenusa = getHipotenusa(start, end);
								
		return catetoOposto/hipotenusa;
	}
	
	public static Double getHipotenusa(Point start, Point end){
		double catetoAdjacente = 0; 
		
		catetoAdjacente = -start.getX()+end.getX();
				
		double catetoOposto = 0;
		
		catetoOposto = -start.getY()+end.getY();
		
		double hipotenusa = Math.sqrt(Math.pow(catetoAdjacente, 2) + Math.pow(catetoOposto, 2));
	
		return hipotenusa;
	}

	public Double getPass() {
		return pass;
	}

	public void setPass(Double pass) {
		this.pass = pass;
	}

	public Double getError() {
		return error;
	}

	public void setError(Double error) {
		this.error = error;
	}
	
	
	
	
	
}
