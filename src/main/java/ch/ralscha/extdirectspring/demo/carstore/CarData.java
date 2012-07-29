package ch.ralscha.extdirectspring.demo.carstore;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class CarData {

	private final String manufacturer;

	private final String model;

	private final int price;

	private final String wiki;

	private final String img;

	private final ImmutableList<Quality> quality;

	public CarData(String manufacturer, String model, int price, String wiki, String img, Quality... quality) {
		this.manufacturer = manufacturer;
		this.model = model;
		this.price = price;
		this.wiki = wiki;
		this.img = img;
		this.quality = ImmutableList.copyOf(quality);
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getModel() {
		return model;
	}

	public int getPrice() {
		return price;
	}

	public String getWiki() {
		return wiki;
	}

	public String getImg() {
		return img;
	}

	public List<Quality> getQuality() {
		return quality;
	}

}
