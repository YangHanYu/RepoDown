package com.ky.repodown.model;

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

/**
 * 
 * @author HanYu
 *
 */
public class VersionPath {
	private ArtifactVersion artiVersion; 
	private ComparableVersion compVersion;
	private String versionString;
	private Object data;
	
	
	public String getVersionString() {
		return versionString;
	}


	public void setVersionString(String versionString) {
		this.artiVersion = new DefaultArtifactVersion(versionString);
		this.compVersion = new ComparableVersion(versionString);
		this.versionString = versionString;
	}

	public ArtifactVersion getArtiVersion() {
		return artiVersion;
	}


	public ComparableVersion getCompVersion() {
		return compVersion;
	}


	public VersionPath(String versionString, Object data) {
		super();
		this.versionString = versionString;
		this.artiVersion = new DefaultArtifactVersion(versionString);
		this.compVersion = new ComparableVersion(versionString);
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
