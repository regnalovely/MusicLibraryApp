git init
git config --global user.email "regna.lovely@gmail.com"
git config --global user.name "regnalovely"
#git clone https://regnalovely@github.com/lpiem/projet-03.git
git remote add android https://regnalovely@github.com/regnalovely/MusicLibraryApp.git
#git pull android master
git checkout -b second
git add *
git commit -m "Init second music app"
git status
git push android second