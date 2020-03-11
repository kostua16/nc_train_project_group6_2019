<template>
  <div class="app-main-layout">
    <nav class="navbar orange lighten-1">
      <div class="nav-wrapper">
        <div class="navbar-left">
          <a href="#">
            <i class="material-icons black-text">dehaze</i>
          </a>
          <span class="black-text">Личный кабинет</span>
        </div>

        <ul class="right hide-on-small-and-down">
          <li>
            <a
              class="dropdown-trigger black-text"
              href="#"
              data-target="dropdown"
            >
              USER NAME
              <i class="material-icons right">arrow_drop_down</i>
            </a>

            <ul
              id="dropdown"
              class="dropdown-content"
            >
              <li>
                <a
                  href="#"
                  class="black-text"
                >
                  <i class="material-icons">account_circle</i>Профиль
                </a>
              </li>
              <li
                class="divider"
                tabindex="-1"
              />
              <li>
                <a
                  href="#"
                  class="black-text"
                >
                  <i class="material-icons">assignment_return</i>Выйти
                </a>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </nav>

    <ul class="sidenav app-sidenav open">
      <li>
        <a
          href="#"
          class="waves-effect waves-orange pointer"
        >Счет</a>
      </li>
      <li>
        <a
          href="#"
          class="waves-effect waves-orange pointer"
        >Пополнить счет</a>
      </li>
      <li>
        <a
          href="#"
          class="waves-effect waves-orange pointer"
        >Изменить тариф</a>
      </li>
      <li>
        <a
          href="#"
          class="waves-effect waves-orange pointer"
        >История</a>
      </li>
      <li>
        <a
          href="#"
          class="waves-effect waves-orange pointer"
        >Услуги и опции</a>
      </li>
    </ul>

    <main class="app-content">
      <div class="app-page">
        <router-view />
      </div>
    </main>

    <div class="fixed-action-btn">
      <a
        class="btn-floating btn-large blue"
        href="#"
      >
        <i class="large material-icons">add</i>
      </a>
    </div>
  </div>
</template>
