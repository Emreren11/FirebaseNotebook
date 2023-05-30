# FirebaseNotebook
FirebaseNotebook uygulaması, firebase altyapısını kullanarak oluşturulan notları çevrimiçi ortama eş zamanlı olarak kaydeder. Uygulama silinse bile kullanıcı giriş yaptığında notlarını görebilir.

## Login Page
![login](https://github.com/Emreren11/FirebaseNotebook/assets/113580478/97920e21-eafd-4332-b48c-475fb71affc9)

*Sign In* : Firebase ile kontrol sağlar ve giriş yapar.

*Sign Up* : Verilen bilgiler doğrultusunda Firebase içerisine kayıt olunur. Kayıt olma işlemi olduğunda kişinin vermiş olduğu email bilgisi ile bir "collection" oluşturulur.

## Home Page
![home](https://github.com/Emreren11/FirebaseNotebook/assets/113580478/3831f74c-e796-4e3e-bb9c-fb8177ab9d37)

- Kaydedilen notların görüntülendiği kısımdır. 
- Her notun başlığı ve not kısmının belirlenmiş bir bölümü görüntülenir.
- Notların üzerine tıklandığında ***Note Page*** fragmanına yönlendirme yapılır.

## Menu
![menu](https://github.com/Emreren11/FirebaseNotebook/assets/113580478/de712d4f-1a78-4743-af88-e90c52002f2e)

*Add Note* : Yeni bir not eklemek için kullanılır. Tıklandığında **Note Page** fragmanına yönlendirme yapılır.

*Sign Out* : Tıklandığında kullanıcının çıkışı gerçekleşmiş olur ve **Login Page" aktivitesine yönlendirme yapılır. Kullanıcının email adresi **Email** bölümüne eklenir.

## Note Page (New)
![newDetail](https://github.com/Emreren11/FirebaseNotebook/assets/113580478/a49124d5-b8d1-4bea-b6b2-426dfa550e99)

- ***onPause*** çalıştığında kaydetme işlemi gerçekleşir. 
- Başlık bilgisi olmadan kaydetme işlemi tamamlanamaz.

## Note Page (Old)
![oldDetail](https://github.com/Emreren11/FirebaseNotebook/assets/113580478/09c381ac-d912-4f23-865f-f0a850f05ba1)
- Herhangi bir nota tıklandığında silme işlemi görünür olur.
- Silme işlemine tıklandığında not Firebase içerisinden silinir ve ***Home Page*** fragmanına yönlendirme yapılır.
- onPause çalıştığında değişiklikler kaydedilir.
- onPause çalıştığında başlık boş olursa herhangi bir değişiklik yapılmaz
