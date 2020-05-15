package com.chen.commonlib.bean;

public class VersionNew
{
	private boolean hasNewVersion;
	private boolean force;
	private String updates;
	private String version;
	private String url;


	private AllDocumentBean allDocument_version;

	public boolean isHasNewVersion() {
		return hasNewVersion;
	}

	public void setHasNewVersion(boolean hasNewVersion) {
		this.hasNewVersion = hasNewVersion;
	}

	public boolean isForce() {
		return force;
	}

	public void setForce(boolean force) {
		this.force = force;
	}

	public String getUpdates() {
		return updates;
	}

	public void setUpdates(String updates) {
		this.updates = updates;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public AllDocumentBean getAllDocument_version() {
		return allDocument_version;
	}

	public void setAllDocument_version(AllDocumentBean allDocument_version) {
		this.allDocument_version = allDocument_version;
	}

	public static class AllDocumentBean {
		private int id;
		private String name;
		private String code;
		private String version;
		private Object remark;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public Object getRemark() {
			return remark;
		}

		public void setRemark(Object remark) {
			this.remark = remark;
		}
	}
}
