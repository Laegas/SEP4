function initialize() {
    var latlng = new google.maps.LatLng(37.39822, -121.9643936);
    var myOptions = {
        zoom: 14,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        mapTypeControl: false,
        navigationControl: false,
        streetViewControl: false,
        backgroundColor: "#666970"
    };

    document.geocoder = new google.maps.Geocoder();
    document.map = new google.maps.Map(document.getElementById("map_canvas"),myOptions);

    document.zoomIn = function zoomIn() {
        var zoomLevel = document.map.getZoom();
        if (zoomLevel <= 20) document.map.setZoom(zoomLevel + 1);
    }

    document.zoomOut = function zoomOut() {
        var zoomLevel = document.map.getZoom();
        if (zoomLevel > 0) document.map.setZoom(zoomLevel - 1);
    }

    document.setMapTypeRoad = function setMapTypeRoad() {
        document.map.setMapTypeId(google.maps.MapTypeId.ROADMAP);
    }
    document.setMapTypeSatellite = function setMapTypeSatellite() {
        document.map.setMapTypeId(google.maps.MapTypeId.SATELLITE);
    }
    document.setMapTypeHybrid = function setMapTypeHybrid() {
        document.map.setMapTypeId(google.maps.MapTypeId.HYBRID);
    }
    document.setMapTypeTerrain = function setMapTypeTerrain() {
        document.map.setMapTypeId(google.maps.MapTypeId.TERRAIN);
    }

    document.goToLocation = function goToLocation(searchString) {
        document.geocoder.geocode( {'address': searchString}, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                document.map.setCenter(results[0].geometry.location);
            } else {
                alert("Geocode was not successful for the following reason: " + status);
            }
        });
    }
}