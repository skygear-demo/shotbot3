import skygear

from urllib.request import urlopen
from base64 import b64encode

@skygear.op("asset-proxy")
def asset_proxy(assetURL):
    # TODO: validate URL
    resp = urlopen(assetURL)
    return {"assetURL": assetURL,
            "resourceURL": resp.geturl()}
