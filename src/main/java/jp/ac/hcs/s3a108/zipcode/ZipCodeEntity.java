package jp.ac.hcs.s3a108.zipcode;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ZipCodeEntity {
	private String status;
	private String message;
	private List<ZipCodeData> results = new ArrayList<ZipCodeData> ();
}