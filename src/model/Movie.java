package model;

import java.io.Serializable;
import org.postgresql.util.PGmoney;
import java.sql.Date;
import java.util.List;
import java.util.Map;

//This is the Customer JavaBean 
public class Movie implements Serializable {
	@Override
	public String toString() {
		return "release_year=" + release_year + ";length=" + length + ";title=" + title + ";genre=" + genre
				+ ";actor=" + actor + ";actress=" + actress + ";director=" + director + ";inventory=" + inventory
				+ ";price=" + price + ";product_id=" + product_id + ";";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actor == null) ? 0 : actor.hashCode());
		result = prime * result + ((actress == null) ? 0 : actress.hashCode());
		result = prime * result + ((director == null) ? 0 : director.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + inventory;
		result = prime * result + length;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + product_id;
		result = prime * result + release_year;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (actor == null) {
			if (other.actor != null)
				return false;
		} else if (!actor.equals(other.actor))
			return false;
		if (actress == null) {
			if (other.actress != null)
				return false;
		} else if (!actress.equals(other.actress))
			return false;
		if (director == null) {
			if (other.director != null)
				return false;
		} else if (!director.equals(other.director))
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (inventory != other.inventory)
			return false;
		if (length != other.length)
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (product_id != other.product_id)
			return false;
		if (release_year != other.release_year)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	private int release_year;
	private int length;
	private String title;
	private String genre;
	private String actor;
	private String actress;
	private String director;
	private int inventory;
	private double price;
	private int product_id;
	
	public Movie(){
	}
	
	public Movie createMovie(Map<String, String> movieMap) {
		Movie m = new Movie();
		m.setActor(movieMap.get("actor"));
		m.setActress(movieMap.get("actress"));
		m.setDirector(movieMap.get("director"));
		m.setGenre(movieMap.get("genre"));
		m.setInventory(Integer.parseInt(movieMap.get("inventory")));
		m.setLength(Integer.parseInt(movieMap.get("length")));
		m.setPrice(Double.parseDouble(movieMap.get("price")));
		m.setProduct_id(Integer.parseInt(movieMap.get("product_id")));
		m.setRelease_year(Integer.parseInt(movieMap.get("release_year")));
		m.setTitle(movieMap.get("title"));
		return m;
	}
	
	public int getRelease_year() {
		return release_year;
	}

	public void setRelease_year(int release_year) {
		this.release_year = release_year;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getActress() {
		return actress;
	}

	public void setActress(String actress) {
		this.actress = actress;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
}
