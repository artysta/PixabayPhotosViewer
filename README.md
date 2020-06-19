# PixabayPhotosViewer

Aplikacja wykonana na potrzeby projektu zaliczeniowego z przedmiotu **Programowanie urządzeń mobilnych** w **Wyższej Szkole Ekonomii i Informatyki w Krakowie**.

## #1 O aplikacji

Aplikacja służy m.in. do przeglądania zdjęć pochodzących z jednej z popularniejszych stron stockowych.

Do działania aplikacji potrzebny jest **klucz API**, który powinien zostać umieszczony w pliku `apikey.properties` znajdującym się w głównym katalogu aplikacji.

Zawartość pliku powinna wyglądać następująco:

`PIXABAY_API = "TWÓJ_KLUCZ_API"`

Klucz otrzymać można po zarejestrowaniu się na stronie https://pixabay.com/api/docs/.

Aplikacja wykorzystuje zewnętrzne biblioteki:
 - [Volley](https://github.com/google/volley) - do parsowania plików JSON,
 - [greenDAO ORM](https://greenrobot.org/greendao/) - do zapisywania danych na temat zdjęć do bazy SQLite,
 - [Picasso](https://github.com/square/picasso) - do pobierania zdjęć.

## #2 Kilka zrzutów ekranu

<table>
 <tr>
  <td>
   <img src="/screenshots/screenshot-1.jpg" alt="screenshot-1.png"/>
  </td>
  <td>
   <img src="/screenshots/screenshot-2.jpg" alt="screenshot-1.png"/>
  </td>
  <td>
   <img src="/screenshots/screenshot-3.jpg" alt="screenshot-1.png"/>
  </td>
 </tr>
</table>
