var map1 = initialize('#home', s1_layer_aurin);
var map2 = initialize('#S2', s2_layer_aurin);
var map3 = initialize('#S3', s3_layer_aurin);
var map4 = initialize('#S4', s4_layer_aurin);
// var map1 = initialize('s1-map', s1_layer_aurin);
// var map1 = initialize('s1-map', s1_layer_aurin);
var map1_aurin, map1_twitter;
var map2_aurin, map2_twitter;
var map3_aurin, map3_twitter;
var map4_aurin, map4_twitter;
google.maps.event.addDomListener(
    window,
    'load',
    map1);
google.maps.event.addDomListener(
    window,
    'load',
    map2);
google.maps.event.addDomListener(
    window,
    'load',
    map3);
google.maps.event.addDomListener(
    window,
    'load',
    map4);
// google.maps.event.addDomListener(
//     window,
//     'load',
//     map3);

function initialize(section, initLayer) {
  google.maps.visualRefresh = true;
  var mapDiv = $(section + ' #googft-mapCanvas')[0];
  mapDiv.style.width = '100%';
  mapDiv.style.height = '600px';
  var map = new google.maps.Map(mapDiv, {
    center: new google.maps.LatLng(-37.059016926771996, 145.55707112100004),
    zoom: 7,
  });
  var style = [
    {
      featureType: 'all',
      elementType: 'all',
      stylers: [
        { saturation: -4 }
      ]
    }
  ];
  var styledMapType = new google.maps.StyledMapType(style, {
    map: map,
    name: 'Styled Map'
  });
  map.mapTypes.set('map-style', styledMapType);
  map.setMapTypeId('map-style');

  map.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(
    $(section + ' #googft-legend-open')[0]);
  map.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(
    $(section + ' #googft-legend')[0]);

  map.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(
    $(section + ' #googft-legend-open')[1]);
  map.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(
    $(section + ' #googft-legend')[1]);

  addLayer(map, initLayer);
  return map;
}

function addLayer(map, layer){
  var newLayer = layer(map);
  var fusionLayer = new google.maps.FusionTablesLayer();
  fusionLayer.setMap(map);
}

function removeLayer(map, layer){
  layer.setMap(null);
}


//Jquery functions
$(document).ready(function(){
  setInterval("ajaxRequest()", 5000);

  $('#home #checkbox-aurin').change(function(){
    if($(this).is(":checked")) {
      $('#home #googft-legend-open').first().show();
      $('#home #googft-legend').first().show();
      addLayer(map1, s1_layer_aurin);
    }else {
      $('#home #googft-legend-open').first().hide();
      $('#home #googft-legend').first().hide();
      removeLayer(map1, map1_aurin);
    }
  });

  $('#home #checkbox-twitter').change(function(){
    if($(this).is(":checked")) {
      $('#home #googft-legend-open').last().show();
      $('#home #googft-legend').last().show();
      addLayer(map1, s1_layer_twitter);
    }else {
      $('#home #googft-legend-open').last().hide();
      $('#home #googft-legend').last().hide();
      removeLayer(map1, map1_twitter);
    }
  });

  $('#S2 #checkbox-aurin').change(function(){
    if($(this).is(":checked")) {
      $('#S2 #googft-legend-open').first().show();
      $('#S2 #googft-legend').first().show();
      addLayer(map2, s2_layer_aurin);
    }else {
      $('#S2 #googft-legend-open').first().hide();
      $('#S2 #googft-legend').first().hide();
      removeLayer(map2, map2_aurin);
    }
  });

  $('#S2 #checkbox-twitter').change(function(){
    if($(this).is(":checked")) {
      $('#S2 #googft-legend-open').last().show();
      $('#S2 #googft-legend').last().show();
      addLayer(map2, s2_layer_twitter);
    }else {
      $('#S2 #googft-legend-open').last().show();
      $('#S2 #googft-legend').last().show();
      removeLayer(map2, map2_twitter);
    }
  });

  $('#S3 #checkbox-aurin').change(function(){
    if($(this).is(":checked")) {
      $('#S3 #googft-legend-open').first().show();
      $('#S3 #googft-legend').first().show();
      addLayer(map3, s3_layer_aurin);
    }else {
      $('#S3 #googft-legend-open').first().hide();
      $('#S3 #googft-legend').first().hide();
      removeLayer(map3, map3_aurin);
    }
  });

  $('#S3 #checkbox-twitter').change(function(){
    if($(this).is(":checked")) {
      $('#S3 #googft-legend-open').last().show();
      $('#S3 #googft-legend').last().show();
      addLayer(map3, s3_layer_twitter);
    }else {
      $('#S3 #googft-legend-open').last().hide();
      $('#S3 #googft-legend').last().hide();
      removeLayer(map3, map3_twitter);
    }
  });

  $('#S4 #checkbox-aurin').change(function(){
    if($(this).is(":checked")) {
      $('#S4 #googft-legend-open').first().show();
      $('#S4 #googft-legend').first().show();
      addLayer(map4, s4_layer_aurin);
    }else {
      $('#S4 #googft-legend-open').first().hide();
      $('#S4 #googft-legend').first().hide();
      removeLayer(map4, map4_aurin);
    }
  });

  $('#S4 #checkbox-twitter').change(function(){
    if($(this).is(":checked")) {
      $('#S4 #googft-legend-open').last().show();
      $('#S4 #googft-legend').last().show();
      addLayer(map4, s4_layer_twitter);
    }else {
      $('#S4 #googft-legend-open').last().hide();
      $('#S4 #googft-legend').last().hide();
      removeLayer(map4, map4_twitter);
    }
  });
})

function showTwitter(location, map, text){

  var infowindow = new google.maps.InfoWindow({
        content: text
      });
  var marker = new google.maps.Marker({
    position: location,
    map: map,
  });
  infowindow.open(map, marker);
  map.setCenter(location);

  setTimeout(function() {
      marker.setVisible(false);
      infowindow.close();
  }, 3000);
}

function showTwitterByAddress(address, map, text){
  var geocoder = new google.maps.Geocoder();
  var location = {lat: -37.81,lng: 144.96};

  if (address.toLocaleLowerCase().indexOf('melbourne') >= 0) {
    var lat = -37.814107 + Math.random()/2;
    var lng = 144.96327999999994 + (Math.random()-0.5);
    location = {
      lat: lat,
      lng: lng
    }
    showTwitter(location, map, text);
    return;
  }

  if (address.toLocaleLowerCase().indexOf('australia') < 0) {
    address = address + ', Australia';
  }


  geocoder.geocode( { 'address': address},
    function(results, status) {
      if (status == google.maps.GeocoderStatus.OK)
      {
          location = {
            lat: results[0].geometry.location.lat(),
            lng: results[0].geometry.location.lng()
          }
          showTwitter(location, map, text);
      }
  });
}

function ajaxRequest() {
    $.ajax({
        url: "http://localhost:8080/getNewTweet",
        type: "Get",
        dataType: "json",
        async: false,
        data: {
            currentTime: new Date().getTime()
        },
        success: function(data) {
            var twitterList = data.list;

            for (var i = 0; i < twitterList.length; i++) {
              var twitter = twitterList[i];
              var location;
              if (twitter['geolocationLatitude'] == 666
                && twitter['geolocationLongitude'] == 666) {
                  //Find location by api
                  address = twitter['placeName'];
                  showTwitterByAddress(address, map1, twitter['text']);
                  showTwitterByAddress(address, map2, twitter['text']);
                  showTwitterByAddress(address, map3, twitter['text']);
                  showTwitterByAddress(address, map4, twitter['text']);
              }else {
                location = {lat: twitter['geolocationLatitude'],
                            lng: twitter['geolocationLongitude']};
                showTwitter(location, map1, twitter['text']);
                showTwitter(location, map2, twitter['text']);
                showTwitter(location, map3, twitter['text']);
                showTwitter(location, map4, twitter['text']);
              }

            }
        },
        error: function() {
            alert("error");
        }
    });
}

//Fusion map info

function s1_layer_aurin(map){
  $('#home #googft-legend-open').last().hide();
  $('#home #googft-legend').last().hide();
  map1_aurin = new google.maps.FusionTablesLayer({
      map: map,
      heatmap: { enabled: false },
      query: {
        select: "col0\x3e\x3e1",
        from: "1mmMt-Zymc5D0i6X1g25udjwToAt6CEI0mDbKrea9",
        where: ""
      },
      options: {
        styleId: 2,
        templateId: 2
      }
    });

  return map1_aurin;
}

function s1_layer_twitter(map){
  map1_twitter = new google.maps.FusionTablesLayer({
      map: map,
      heatmap: { enabled: false },
      query: {
        select: "col0",
        from: "1Gp8uk6c2V4jtE70bGeGE9uWL7FIWzKIGv3Ss6LWL",
        where: ""
      },
      options: {
        styleId: 2,
        templateId: 2
      }
    });

  return map1_twitter;
}

function s2_layer_aurin(map){
  $('#S2 #googft-legend-open').last().hide();
  $('#S2 #googft-legend').last().hide();
  var layer = new google.maps.FusionTablesLayer({
     map: map,
     heatmap: { enabled: false },
     query: {
       select: "col5",
       from: "1LOAPw06_2__V-OUuF8mt5X9lmlNrQIM7Tc69WQt-",
       where: ""
     },
     options: {
       styleId: 2,
       templateId: 2
     }
   });

  map2_aurin = new google.maps.FusionTablesLayer({
      map: map,
      heatmap: { enabled: false },
      query: {
        select: "col0",
        from: "1mHnbVtbAwAYifl9ildcQGkSNhbqukvb4iT4AUB5T",
        where: ""
      },
      options: {
        styleId: 2,
        templateId: 2
      }
    });

  return map2_aurin;
}

function s2_layer_twitter(map){
  map2_twitter = new google.maps.FusionTablesLayer({
      map: map,
      heatmap: { enabled: false },
      query: {
        select: "col0",
        from: "1QHD6sR83kyLE4FGFMhVd6aOGsuS5G8mTmiv1mlSb",
        where: ""
      },
      options: {
        styleId: 2,
        templateId: 3
      }
    });

  return map2_twitter;
}

function s3_layer_aurin(map){
  $('#S3 #googft-legend-open').last().hide();
  $('#S3 #googft-legend').last().hide();
  map3_aurin = new google.maps.FusionTablesLayer({
      map: map,
      heatmap: { enabled: false },
      query: {
        select: "col0\x3e\x3e1",
        from: "1QCX4z0R4OccsxyCgJJj70Af6nbfzqU-U0NCHU2bE",
        where: ""
      },
      options: {
        styleId: 2,
        templateId: 2
      }
    });

  return map3_aurin;
}

function s3_layer_twitter(map){
  map3_twitter = new google.maps.FusionTablesLayer({
      map: map,
      heatmap: { enabled: false },
      query: {
        select: "col0",
        from: "1-hceNmLiwRIgImQdE7nn9-t73Jgy5jNEBPDXkYzm",
        where: ""
      },
      options: {
        styleId: 2,
        templateId: 2
      }
    });

  return map3_twitter;
}

function s4_layer_aurin(map){
  $('#S4 #googft-legend-open').last().hide();
  $('#S4 #googft-legend').last().hide();
  map4_aurin = new google.maps.FusionTablesLayer({
      map: map,
      heatmap: { enabled: false },
      query: {
        select: "col0\x3e\x3e1",
        from: "1fwaEdXXQp-CWEEnQciX5qGCcgRWGnCO1yFiO6vif",
        where: ""
      },
      options: {
        styleId: 2,
        templateId: 2
      }
    });

  return map4_aurin;
}

function s4_layer_twitter(map){
  map4_twitter = new google.maps.FusionTablesLayer({
      map: map,
      heatmap: { enabled: false },
      query: {
        select: "col0",
        from: "1-hceNmLiwRIgImQdE7nn9-t73Jgy5jNEBPDXkYzm",
        where: ""
      },
      options: {
        styleId: 2,
        templateId: 2
      }
    });

  return map4_twitter;
}
